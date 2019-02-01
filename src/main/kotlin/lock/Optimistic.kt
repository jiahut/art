package lock

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    val url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false"
    val driver = "com.mysql.jdbc.Driver"
    val connect = Database.connect(url, driver, "root", "mysql")
    val pool = Executors.newFixedThreadPool(2)

    transaction {
        create(Users, Accounts)

        val acountId = Accounts.insert {
            it[money] = 0
            it[version] = 1
        } get Accounts.id

        Users.insert {
            it[id] = "044166"
            it[name] = "zhangzhijia"
            it[account] = acountId
        }

        for (user in Users.selectAll()) {
            println("${user[Users.name]}")
        }

    }
    pool.submit {
        Accounts.select({ Users.name eq "zhangzhijia" }).forEach {
            val the_version = it[Accounts.version]
            val the_money = it[Accounts.money]
            val the_account_id = it[Accounts.id]
            println(the_account_id)
            println(the_version)
            Accounts.update({ (Accounts.version eq the_version) and (Accounts.id eq the_account_id) }) {
                it[money] = the_money + 100
                it[version] = the_version + 1
            }
        }
        drop(Users, Accounts)
    }

    while (!pool.isTerminated) { }

}


object Users : Table() {
    val id = varchar("id", 10).primaryKey() // Column<String>
    val name = varchar("name", length = 50) // Column<String>
    val account = (integer("account_id") references Accounts.id).nullable()
}

object Accounts : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val money = long("money")
    val version = long("version")
}

