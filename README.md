**Для получения АОП лога выходить из программы нажатием клавиши Enter в терминале**

**Users:**
1) admin - 100 (admin)
2) user1 - 100 (customer)
3) user2 - 100 (customer)

**SOAP request:**
[POST] http://localhost:8189/ws
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.pankov.ru/spring/ws/products">
    <soapenv:Header/>
    <soapenv:Body>
        <f:getAllProductsRequest/>
    </soapenv:Body>
</soapenv:Envelope>

**Выполнено:**
1) Убрано AOP
2) Добавлен SOAP

**TODO:**
1) Корзина сессионная (нужно менять на бд/редис/другое)
2) Найти решение с удалением продуктов, которые есть в заказах (вариант: добавить поле active)
3) Разделить дтошки (сейчас прокидываются с полями null)