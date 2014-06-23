package uberconf.model

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.transform.CompileStatic
import javax.inject.Inject

@CompileStatic
class PhotoDB {

  private final Sql sql

  @Inject
  PhotoDB(Sql sql) {
    this.sql = sql
  }

  void createTable() {
    sql.execute "drop table if exists photo"
    sql.execute "create table photo (id bigint auto_increment, name varchar(255), data blob)"
  }

  List<GroovyRowResult> all() {
    sql.rows("select id, name, data from photo")
  }

  GroovyRowResult findOne(Long id) {
    sql.firstRow("select id, name, data from photo where id = $id")
  }

  GroovyRowResult findOneByName(String name) {
    sql.firstRow("select id, name, data from photo where name = $name")
  }

  Long save(String name, byte[] data) {
    def rows = sql.executeInsert "insert into photo (name, data) values ($name, $data)"
    rows[0][0] as Long
  }

  void remove(Long id) {
    sql.execute "delete from photo where id = $id"
  }
}
