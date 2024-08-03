import requests
from bs4 import BeautifulSoup
import pandas as pd

ipone_info = list() # 아이폰 검색 정보 저장
html = requests.get('https://www.gmarket.co.kr/n/search?keyword=%EC%95%84%EC%9D%B4%ED%8F%B014&f=p:1000000^')
bs = BeautifulSoup(html.content, 'html.parser')

for box in bs.find_all('div', class_='box__component box__component-itemcard box__component-itemcard--general'):
    name = box.find('span', class_='text__item').string
    tag_img = box.find('span', class_='image__awards-points')
    #award_point = tag_img.get('style')
    #print(award_point)
    
    tag_span = box.find_all('span', class_='text text__value')
    if len(tag_span) == 2:
        price_original = tag_span[0].string
        discount = tag_span[1].string
    else : 
        price_original = "None"
        discount = "None"
        
    price_seller = box.find('strong', class_='text text__value').string
    seller = box.find('span', class_='text__seller')
    
    print(len(box.find_all(class_='text text__value')))

    if seller is not None:
        print(name.string)
        name = box.find('span', class_='text__item').string
        if price_original is not None :
            print(price_original)
            print(discount)
        else : print("None")
        
        print(price_seller.string)
        print(seller.string)
  
    ipone_info.append([name]+[price_original]+[discount]+[price_seller]);
print(f'RESULT : \r\n{ipone_info}')    
ipone_info_tbl = pd.DataFrame(data=ipone_info, columns=('상품이름', '원가', '할인율', '판매가'))
ipone_info_tbl.to_csv('./Ipone_table_EXCEL.csv', encoding='cp949', mode='w', index=True)
ipone_info_tbl.to_csv('./Ipone_table.csv', encoding='utf-8', mode='w', index=True)
#Search_data1 = bs.find_all('span', class_='text__item')
"""
for name in bs.find_all('span', class_='text__item'):
    print(name.string)

print("=================================================")

for price_original in bs.find_all('span', class_='text text__value'):
    print(price_original.string)

print("=================================================")
    
for price_seller in bs.find_all('strong', class_='text text__value'):
    print(price_seller.string)   

print("=================================================")
    
for seller in bs.find_all('span', class_='text__seller'):
    print(seller.string)     
""" 

#Search_data1 = bs.find_all('strong', class_='text text__value')
#for data2 in bs.find_all('a', class_='link__item'):
#    print(data2.attrs['data-montelena-keyword'])