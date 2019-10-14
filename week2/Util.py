import pymysql
import pandas

def init_table(user, passwd, host, port, dbname, tablename):
    db = pymysql.connect(host=host, port=port, db=dbname,
                         user=user, passwd=passwd, charset='utf8')
    cursor = db.cursor()
    createSql = '''create table {}(
                    日期 varchar(50),
                    前开盘价 varchar(50),
                    开盘价 varchar(50),
                    最高价 varchar(50),
                    最低价 varchar(50),
                    收盘价 varchar(50),
                    成交量 varchar(50),
                    成交金额 varchar(50),
                    涨跌 varchar(50),
                    涨跌幅 varchar(50),
                    均价 varchar(50),
                    换手率 varchar(50),
                    A股流通市值 varchar(50),
                    B股流通市值 varchar(50),
                    总市值 varchar(50),
                    A股流通股本 varchar(50),
                    B股流通股本 varchar(50),
                    总股本 varchar(50),
                    市盈率 varchar(50),
                    市净率 varchar(50),
                    市销率 varchar(50),
                    市现率 varchar(50))
    '''.format(tablename)
    cursor.execute(createSql)
    db.commit()
    df = pandas.read_csv('data.CSV', encoding='ISO-8859-1')
    r, c = df.shape
    for i in range(r):
        insertSql = 'insert into {} values (\''.format(tablename)
        for j in range(2, c-1):
            insertSql += str(df.iloc[i, j])
            insertSql += '\',\''
        insertSql = insertSql[:-2] + ')'
        cursor.execute(insertSql)
        db.commit()

def test(user, passwd, host, port, dbname, tablename):
    db = pymysql.connect(host=host, port=port, db=dbname,
                         user=user, passwd=passwd, charset='utf8')
    cursor = db.cursor()
    field = '前收盘价'
    table = 't'
    date = '\'1999/11/10\''
    sql = 'select {} from {} where 日期 = {}'.format(field, table, date)
    cursor.execute(sql)
    db.commit()

#test('root', 'qwe59578', 'localhost', 3306, 'SA_2', 'data')
#pandas.read_csv('data.CSV', encoding='ISO-8859-1')
init_table('root', 'qwe59578', 'localhost', 3306, 'SA_2', 'data')
