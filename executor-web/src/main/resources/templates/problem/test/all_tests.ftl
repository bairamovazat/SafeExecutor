<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html>
<#include "../../head.ftl">
<body>
<#include "../../header.ftl">
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
                <h3>Список тестов для задачи "${model.problem.name}"
                    <a href="../../${model.problem.id}">
                        <button class="btn btn-primary">
                            Назад
                        </button>
                    </a>
                </h3>
            </div>
            <div>
                <a href="create">
                    <button class="btn btn-primary">Новый тест</button>
                </a>
            </div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Входные данные</th>
                    <th scope="col">Выходные данные</th>
                    <th scope="col">Rank</th>
                    <th scope="col">Действия</th>
                </tr>
                </thead>
                <tbody>
                <#list model.testCases as test>
                    <tr>
                        <th scope="row"><a href="create?testId=${test.id}">${test.id!}</a></th>
                        <td>${test.inputData!}</td>
                        <td>${test.outputData!}</td>
                        <td>${test.rank!}</td>
                        <td>
                            <a href="delete?testId=${test.id}">
                                <button class="btn btn-danger">
                                    Удалить
                                </button>
                            </a>

                            <a href="create?testId=${test.id}">
                                <button class="btn btn-primary">
                                    Изменить
                                </button>
                            </a>
                        </td>

                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
    </div>
</div>

<#include "../../footer.ftl">
</body>
</html>
