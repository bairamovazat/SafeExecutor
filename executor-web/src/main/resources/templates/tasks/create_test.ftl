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
            <h3>
                Добавление теста к задаче "${model.task.name}"
                <a href="all">
                    <button class="btn btn-primary">
                        Назад
                    </button>
                </a>
            </h3>
            <form action="create" method='POST'>
                <div class="form-group">
                    <#if errors??>
                        <#list errors as e>
                            <div class="alert alert-danger" role="alert">${e}</div>
                        </#list>
                    </#if>
                </div>
                <div class="form-group">
                    <#if info??>
                        <div class="alert alert-success" role="alert">${info}</div>
                    </#if>
                </div>

                <input style="display: none;" name="testId"
                       value="${model.test???then(model.test.id, "")}">

                <div class="form-group">
                    <h4>
                        ${model.test???then("Изменение теста " + model.test.id, "Создание нового теста")}
                    </h4>
                </div>

                <div class="form-group">
                    <label for="inputInputDescription">Входные данные</label>
                    <textarea name="inputData" rows="5" class="form-control" id="inputInputDescription"
                              maxlength="255">${model.test???then(model.test.inputData, "")}</textarea>
                </div>

                <div class="form-group">
                    <label for="inputOutputDescription">Выходные данные</label>
                    <textarea name="outputData" rows="5" class="form-control" id="inputOutputDescription"
                              maxlength="255" >${model.test???then(model.test.outputData, "")}</textarea>
                </div>

                <div class="form-group">
                    <input class="form-control" type="submit">
                </div>
            </form>
        </div>
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
    </div>
</div>

<#include "../footer.ftl">
</body>
</html>
