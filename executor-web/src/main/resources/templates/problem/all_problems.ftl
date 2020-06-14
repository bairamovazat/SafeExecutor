<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html>
<#include "../head.ftl">
<body>
<#include "../header.ftl">
<div class="container-fluid">
    <div class="row">
        <div class="col-12">
            <#if errors??>
                <#list errors as e>
                    <div class="alert alert-danger" role="alert">${e}</div>
                </#list>
            </#if>
        </div>
        <div class="col-12">
            <#if info??>
                <div class="alert alert-success" role="alert">${info}</div>
            </#if>
        </div>
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
        <div class="col-12 col-sm-12 col-md-10 col-lg-8 col-xs-6">
            <div style="text-align: center">
                <h3> Список задач
                    <#if model.user.isPresent() && model.user.get().hasRole("CREATOR")>
                        <a href="create">
                            <button class="btn btn-primary">
                                Добавить
                            </button>
                        </a>
                    </#if>
                </h3>
            </div>

            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Название</th>
                        <#if model.user.isPresent() && model.user.get().hasRole("CREATOR")>
                            <th scope="col">Изменить</th>
                            <th scope="col">Тесты</th>
                            <th scope="col">Удалить</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#list model.problems as problem>
                        <tr>
                            <th scope="row"><a href="${problem.id}">${problem.id!}</a></th>
                            <td>${problem.name!}</td>
                            <#if model.user.isPresent() && model.user.get().hasRole("CREATOR")>
                                <td>
                                    <a href="create?problemId=${problem.id}">
                                        <button class="btn btn-primary">
                                            Изменить
                                        </button>
                                    </a>
                                </td>
                                <td>
                                    <a href="${problem.id}/test/all">
                                        <button class="btn btn-primary">
                                            Тесты
                                        </button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete?problemId=${problem.id}">
                                        <button type="button" class="btn btn-danger" data-toggle="modal"
                                                data-target="#exampleModal">
                                            Удалить
                                        </button>
                                    </a>
                                </td>
                            </#if>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <nav aria-label="navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${((model.currentPage == 0)?string("disabled",""))}">
                        <a class="page-link" href="?page=${model.currentPage - 1}" tabindex="-1">Previous</a>
                    </li>

                    <#list model.pageList as page>
                        <li class="page-item ${((page == model.currentPage)?string("active",""))}">
                            <a class="page-link" href="?page=${page}">
                                ${page}
                            </a>
                        </li>
                    </#list>

                    <li class="page-item
                        ${((model.pageList?size <= 0 || model.currentPage == model.pageList[model.pageList?size - 1])?string("disabled",""))}
                        ">
                        <a class="page-link" href="?page=${model.currentPage + 1}">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
    </div>
</div>
<#include "../footer.ftl">
</body>
</html>
