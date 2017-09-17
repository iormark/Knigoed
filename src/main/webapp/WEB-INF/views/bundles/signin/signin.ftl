<div class="signin-wrap">
    <span class="signin-helper"></span>
    <div class="signin">
        <div class="message">
            <div class="message__inner">
                <h4 class="message__h4">Есть проблема!</h4>
                <div class="message__content">Мы не можем найти учетную запись с этим адресом электронной почты</div>
            </div>
        </div>

        <h2 class="signin__title">Добро пожаловать!</h2>
        <p class="signin__description"></p>

        <form method="post" action="/signin" class="signin-form" id="signin-form">
            <input type="text" name="email" value="iormark@yandex.ru" placeholder="Email пользователя" class="signin-form__input">
            <div class="field-message" id="field-message-email"></div>

            <input type="password" name="password" value="qa7zswerSmp" placeholder="Пароль" class="signin-form__input">
            <div class="field-message" id="field-message-password"></div>

            <input type="hidden" name="remember-me" value="on">
            <input class="btn btn_green signin__btn" type="submit" value="Войти">
        </form>

        <div class="signin-footer">
            <a href="/signin/password" class="signin-footer__password">напомнить?</a>
        </div>
    </div>
</div>