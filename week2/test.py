import re


string = '2016/5/4 2013-2-3'

res = re.findall(r"(\d{4}[/-]\d{1,2}[/-]\d{1,2})", string)

string2 = '前开盘价\n'

res2 = re.split(r'[\s:;]+', string2)

print(res2)