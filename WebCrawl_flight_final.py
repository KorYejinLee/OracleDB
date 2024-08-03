import requests
from bs4 import BeautifulSoup
import pandas as pd

ipone_info = list() # 아이폰 검색 정보 저장
html = requests.get('https://www.gmarket.co.kr/n/search?keyword=%EC%95%84%EC%9D%B4%ED%8F%B014&f=p:1000000^')
bs = BeautifulSoup(html.content, 'html.parser')

for box in bs.find_all('div', class_='box__component box__component-itemcard box__component-itemcard--general'):
    name = box.find('span', class_='text__item').string
    tag_img = box.find('span', class_='image__awards-points')
    
    tag_span = box.find_all('span', class_='text text__value')
    if len(tag_span) == 2:
        price_original = tag_span[0].string
        discount = tag_span[1].string
    else : 
        price_original = "None"
        discount = "None"
        
    price_seller = box.find('strong', class_='text text__value').string
    ipone_info.append([name]+[price_original]+[discount]+[price_seller]);

    
ipone_info_tbl = pd.DataFrame(data=ipone_info, columns=('상품이름', '원가', '할인율', '판매가'))
#ipone_info_tbl['원가'] = ipone_info_tbl['원가'].replace('None', '0').astype(str).str.replace(',', '').astype(int)
#ipone_info_tbl['할인율'] = ipone_info_tbl['할인율'].astype(str).str.replace('%', '').replace('None', '0').astype(int)
#ipone_info_tbl['판매가'] = ipone_info_tbl['판매가'].astype(str).str.replace(',', '').astype(int)
ipone_info_tbl.to_csv('./Iphone_table_EXCEL.csv', encoding='cp949', mode='w', index=True)
ipone_info_tbl.to_csv('./Iphone_table.csv', encoding='utf-8', mode='w', index=True)

