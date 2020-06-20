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
            <form action="create" method='POST' enctype="multipart/form-data">
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

                <h3>
                <#if model.problem??>
                    Изменение исполняемого файла "${model.executable.name}"
                <#else>
                    Создание нового исполняемого файла
                </#if>
                </h3>

                <input style="display: none;" name="id"
                       value="${model.executable???then(model.executable.id, "")}">

                <div class="form-group">
                    <label for="inputName" class="required">Название выполняемого файла</label>
                    <input type="text" id="inputName" name="name" required="required" class="form-control"
                           placeholder="run" minlength="2" maxlength="32"
                           value="${model.executable???then(model.executable.name, "")}">
                </div>

                <div class="form-group">
                    <label for="inputDescription" class="required">Описание выполняемого файла</label>
                    <input type="text" id="description" name="description" required="required" class="form-control"
                           maxlength="255" value="${model.executable???then(model.executable.description, "")}">
                </div>

                <div class="form-group">
                    <label class="" for="inputType">Тип выполняемого файла</label>
                    <select id="inputType" name="type"
                            class="form-control custom-select form-control">
                        <#if model.types??>
                            <#list model.types as type>
                                <option value="${type}" ${model.executable???then((model.executable.type==type)?then("selected",""),"")}>
                                    ${type}
                                </option>
                            </#list>
                        </#if>
                    </select>
                </div>

                <#if model.executable??>
                    <div class="form-group">
                        <div class="custom-control custom-checkbox">
                            <input type="checkbox" id="inputUpdateFile"
                                   name="updateFile" class="custom-control-input" checked>
                            <label class="custom-control-label" for="inputUpdateFile">
                                Обновить файл ?
                            </label>
                        </div>
                    </div>
                <#else>
                    <div class="form-group" style="display: none">
                        <div class="custom-control custom-checkbox">
                            <input type="checkbox" id="inputUpdateFile"
                                   name="updateFile" class="custom-control-input" checked>
                            <label class="custom-control-label" for="inputUpdateFile">
                                Обновить файл ?
                            </label>
                        </div>
                    </div>
                </#if>

                <div class="form-group" id="inputFileGroup">
                    <label for="inputFile">Выполняемый файл</label>
                    <input type="file" id="inputFile" name="file" required="required" class="form-control"
                           value="${model.executable???then("file?executableId=" + model.executable.id, "")}">
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
<#if model.executable??>
    <script>
        function updateFileInput(enabled) {
            $("#inputFileGroup")[0].style.display = (enabled ? "block" : "none");
            $("#inputFile")[0].required = (enabled ? true : false);

            if (!enabled) {
                $("#inputFile")[0].value = "";
            }
        }

        $(document).ready(function () {
            $("#inputUpdateFile")[0].onchange = (event) => {
                console.log(event);
                updateFileInput(event.target.checked);
            };
        });
    </script>
</#if>

<#include "../footer.ftl">
</body>
</html>
