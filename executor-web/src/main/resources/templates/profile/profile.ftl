<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html>
<#include "../head.ftl">
<body>
<#include "../header.ftl">
<div class="container-fluid">
    <div class="row">
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
        <div class="col-12 col-sm-12 col-md-10 col-lg-8 col-xs-6">
            <div class="card">
                <div class="card-body">
                    <div class="well profile col-12">
                        <div class="col-sm-12 d-flex">
                            <div class="col-xs-12 col-sm-8">
                                <h2>${model.user.get().name}</h2>
                                <p><strong>Логин: </strong> ${model.user.get().login} </p>
                                <p><strong>Почта: </strong>${model.user.get().email}</p>
                                <p><strong>Статус: </strong>${model.user.get().state}</p>
<#--                                <#if model.user.get().role != "USER">-->
<#--                                    <p><strong>Роль: </strong>${model.user.get().role}</p>-->
<#--                                </#if>-->
                                <#--                        <p><strong>Skills: </strong>-->
                                <#--                            <span class="tags">html5</span>-->
                                <#--                            <span class="tags">css3</span>-->
                                <#--                            <span class="tags">jquery</span>-->
                                <#--                            <span class="tags">bootstrap3</span>-->
                                <#--                        </p>-->
                            </div>
                            <div class="col-xs-12 col-sm-4 text-center" style="padding-top: 25px;">
                                <figure>
                                    <img style="width: 100px; height: 100px;" src="resources/user.png" alt=""
                                         class="img-circle img-responsive">
                                    <#--                            <figcaption class="ratings">-->
                                    <#--                                <p>Ratings-->
                                    <#--                                    <a href="#">-->
                                    <#--                                        <span class="fa fa-star">1</span>-->
                                    <#--                                    </a>-->
                                    <#--                                    <a href="#">-->
                                    <#--                                        <span class="fa fa-star">2</span>-->
                                    <#--                                    </a>-->
                                    <#--                                    <a href="#">-->
                                    <#--                                        <span class="fa fa-star">3</span>-->
                                    <#--                                    </a>-->
                                    <#--                                    <a href="#">-->
                                    <#--                                        <span class="fa fa-star">4</span>-->
                                    <#--                                    </a>-->
                                    <#--                                    <a href="#">-->
                                    <#--                                        <span class="fa fa-star-o">5</span>-->
                                    <#--                                    </a>-->
                                    <#--                                </p>-->
                                    <#--                            </figcaption>-->
                                </figure>
                            </div>
                        </div>
                        <div class="col-xs-12 divider text-center d-flex">
                            <div class="col-xs-12 col-sm-4 emphasis">
                                <h2><strong></strong></h2>
                                <p>
                                    <small>Мои задачи</small>
                                </p>
                                <a href="profile/solutions/all">
                                    <button class="btn btn-success btn-block">
                                    <span class="fa fa-plus-circle">

                                    </span>
                                        Просмотреть
                                    </button>
                                </a>
                            </div>
<#--                            <div class="col-xs-12 col-sm-4 emphasis">-->
<#--                                <h2><strong>245</strong></h2>-->
<#--                                <p>-->
<#--                                    <small>Following</small>-->
<#--                                </p>-->
<#--                                <button class="btn btn-info btn-block"><span class="fa fa-user"></span> View Profile-->
<#--                                </button>-->
<#--                            </div>-->
<#--                            <div class="col-xs-12 col-sm-4 emphasis">-->
<#--                                <h2><strong>43</strong></h2>-->
<#--                                <p>-->
<#--                                    <small>Snippets</small>-->
<#--                                </p>-->
<#--                                <div class="btn-group dropup btn-block">-->
<#--                                    <button type="button" class="btn btn-primary"><span class="fa fa-gear"></span>-->
<#--                                        Options-->
<#--                                    </button>-->
<#--                                    <button type="button" class="btn btn-primary dropdown-toggle"-->
<#--                                            data-toggle="dropdown">-->
<#--                                        <span class="caret"></span>-->
<#--                                        <span class="sr-only">Toggle Dropdown</span>-->
<#--                                    </button>-->
<#--                                    <ul class="dropdown-menu text-left" role="menu">-->
<#--                                        <li><a href="#"><span class="fa fa-envelope pull-right"></span> Send an email-->
<#--                                            </a></li>-->
<#--                                        <li><a href="#"><span class="fa fa-list pull-right"></span> Add or remove from a-->
<#--                                                list </a></li>-->
<#--                                        <li class="divider"></li>-->
<#--                                        <li><a href="#"><span class="fa fa-warning pull-right"></span>Report this user-->
<#--                                                for spam</a></li>-->
<#--                                        <li class="divider"></li>-->
<#--                                        <li><a href="#" class="btn disabled" role="button"> Unfollow </a></li>-->
<#--                                    </ul>-->
<#--                                </div>-->
<#--                            </div>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-1 col-lg-2 col-xs-3">

    </div>
</div>
</div>

<#include "../footer.ftl">
</body>
</html>