<#include "/blocks/shop-panel/shop-panel.ftl">

<div class="shop-statistic">

    <div class="shop-statistic-filter">
        Всего переходов за период: <b>${clickAll!0}</b>

        <form class="shop-statistic-filter__form" method="GET">
            Отчетный период с
            <input size="10" type="date" name="startDate" value="2017-08-21"> по
            <input size="10" type="date" name="endDate" value="2017-09-21">
            <input type="submit" value="ok">
        </form>
    </div>


    <table class="table">
        <thead>
        <tr>
            <th class="shop-list__name">Откуда</th>
            <th class="shop-list__status">Куда</th>
            <th>Дата и время</th>
            <th>IP адрес</th>
            <th>Стоимость (руб.)</th>
        </tr>
        </thead>
        <tbody>
        <#list statistics as stat>

        <tr>
            <td>${stat.fromUrl}</td>
            <td>${stat.toUrl}</td>
            <td>${stat.creationDate}</td>
            <td>${stat.clientInfo}</td>
            <td>${stat.status}</td>
        </tr>

        <#else>
        <tr>
            <td colspan="5" class="center">У ${shop.name} нет статистики</td>
        </tr>
        </#list>
        </tbody>
    </table>

</div>