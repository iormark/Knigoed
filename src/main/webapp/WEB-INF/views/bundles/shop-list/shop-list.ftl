<div class="shop-list">
    <table class="table">
        <thead>
        <tr>
            <th class="shop-list__name">Магазин</th>
            <th class="shop-list__status">Статус</th>
            <th>Клики сегодня</th>
            <th>Клики вчера</th>
            <th>Клики за неделю</th>
            <th>Баланс (руб.)</th>
        </tr>
        </thead>
        <tbody>
        <#list shopList as shop>

        <tr>
            <td><a class="shop-list__link" href="/shop/${shop.shopId}">${shop.name}</a></td>
            <td>${shop.status}</td>
            <td class="shop-list__click"><a class="shop-list__link" href="/shop/${shop.shopId}/statistic?">${shop.clickToday}</a></td>
            <td class="shop-list__click"><a class="shop-list__link" href="/shop/${shop.shopId}/statistic?">${shop.clickYesterday}</a></td>
            <td class="shop-list__click"><a class="shop-list__link" href="/shop/${shop.shopId}/statistic?">${shop.clickWeek}</a></td>
            <td class="shop-list__balance"><a class="shop-list__link" href="/shop/${shop.shopId}/setting">${shop.balance}</a></td>
        </tr>

        <#else>
        <div>У вас нет добавленных магазинов.</div>
        </#list>
        </tbody>
    </table>

</div>