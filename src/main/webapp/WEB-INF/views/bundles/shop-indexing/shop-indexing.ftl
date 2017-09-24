<#include "/blocks/shop-panel/shop-panel.ftl">

<div class="shop-indexing">

    <table class="table">
        <thead>
        <tr>
            <th colspan="2">Результат обработки YML-каталога</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td width="30%">Адрес прайс-листа:</td>
            <td>${(shop.settings.feed.url)!"—"}</td>
        </tr>
        <tr>
            <td>Текущая публикация:</td>
            <td>${(shop.lastModified)!"—"}</td>
        </tr>
        <tr>
            <td>Кол-во предложений:</td>
            <td>${(shop.settings.report.count_books)!"—"}</td>
        </tr>
        <tr>
            <td>Статус обработки:</td>
            <td>${(shop.settings.report.error_type)!"—"}</td>
        </tr>
        <tr>
            <td>Кол-во ошибок:</td>
            <td>${(shop.settings.report.count_error)!"—"}</td>
        </tr>
        <tr>
            <td>Кол-во предупреждений:</td>
            <td>${(shop.settings.report.count_warning)!"—"}</td>
        </tr>
        <tr>
            <td>Сообщение:</td>
            <td>
                <div class="shop-indexing__message">
                ${(shop.report.message)!"—"}
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>