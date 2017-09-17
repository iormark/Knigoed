<div class="signup-wrap">
    <span class="signup-helper"></span>
    <div class="signup">
        <div class="message">
            <div class="message__inner">
                <h4 class="message__h4">Есть проблема!</h4>
                <div class="message__content">Мы не можем найти учетную запись с этим адресом электронной почты</div>
            </div>
        </div>

        <h2 class="signup__title">Добро пожаловать!</h2>
        <p class="signup__description"></p>

        <form method="post" action="/signup" class="signup-form" id="signup-form">
            <input type="name" name="name" value="Марк" placeholder="Ваше имя" class="signup-form__input">
            <input type="email" name="email" value="iormark@yandex.ru" placeholder="Email пользователя" class="signup-form__input">
            <input type="password" name="password" value="qa7zswerSmp" placeholder="Пароль" class="signup-form__input">
            <input type="hidden" name="remember-me" value="on">
            <input class="btn btn_green signup__btn" type="submit" value="Войти">
        </form>

        <div class="signup-footer">
            <a href="/signup/password" class="signup-footer__password">напомнить?</a>
        </div>
    </div>
</div>