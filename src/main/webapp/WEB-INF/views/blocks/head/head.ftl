<header id="header">
    <div class="header">
        <a href="/" class="header-logo">
            <img class="header-logo__image" src="http://www.knigoed.info/images/knigoed.png" width="120" height="" alt="Knigoed - поиск книг">
        </a>
        <div class="header-panel">
            Вход
        </div>

        <form class="header-search" method="GET" action="/search">
            <div class="header-search__right">
                <input class="header-search__submit" type="submit" value="Поиск">
                <input type="hidden" name="available" value="true">
            </div>
            <div class="header-search__left">
                <input class="header-search__key" type="text" name="key" value="${key!""}" placeholder="Введите название, автора, isbn...">
            </div>
        </form>

    </div>
</header>