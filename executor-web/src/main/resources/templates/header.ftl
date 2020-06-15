<#import "spring.ftl" as spring />

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <a class="navbar-brand" href="<@spring.url "/"/>">acm.kpfu</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsingNavbarLg">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="navbar-collapse collapse" id="collapsingNavbarLg">
        <ul class="navbar-nav">

            <li class="nav-item">
                <a class="nav-link" href="<@spring.url "/"/>problems/all">Задачи</a>
            </li>

            <#if model.user.isPresent()>
                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url "/"/>profile">Личный кабинет</a>
                </li>

                <#if model.user.get().hasRole("CREATOR")>
                    <li class="nav-item">
                        <a class="nav-link" href="<@spring.url "/"/>executable/all">Executable</a>
                    </li>
                </#if>

                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url "/"/>logout">Выход</a>
                </li>
            </#if>

             <#if !model.user.isPresent()>
                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url "/"/>login">Авторизация</a>
                </li>
             </#if>
        </ul>
    </div>
</nav>