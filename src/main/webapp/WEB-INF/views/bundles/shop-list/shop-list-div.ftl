<div class="shop-list">
    <div class="list__head">
        <div class="list__cell shop-list__cell_name">Магазин</div>
        <div class="list__cell shop-list__cell_status">Статус</div>
        <div class="list__cell shop-list__cell_click">Клики сегодня</div>
        <div class="list__cell shop-list__cell_click">Клики вчера</div>
        <div class="list__cell shop-list__cell_click">Клики за неделю</div>
        <div class="list__cell shop-list__cell_balance">Баланс (руб.)</div>
    </div>
<#list shopList as shop>


    <div class="list__row">
        <div class="list__cell shop-list__cell_name">${shop.name}</div>
        <div class="list__cell shop-list__cell_status">${shop.status}</div>
        <div class="list__cell shop-list__cell_click">${shop.clicksToday}</div>
        <div class="list__cell shop-list__cell_click">${shop.clicksYesterday}</div>
        <div class="list__cell shop-list__cell_click">${shop.clicksWeek}</div>
        <div class="list__cell shop-list__cell_balance">${shop.balance}</div>
    </div>

<#else>
    <div>У вас еще нет добавленных магазинов.</div>
</#list>
</div>