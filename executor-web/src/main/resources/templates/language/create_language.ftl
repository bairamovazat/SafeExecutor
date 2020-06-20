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
                        Изменение языка для тестирования "${model.language.name}"
                    <#else>
                        Создание нового языка для тестирования
                    </#if>
                </h3>

                <input style="display: none;" name="id"
                       value="${model.language???then(model.language.id, "")}">

                <div class="form-group">
                    <label for="inputName" class="required">Название</label>
                    <input type="text" id="inputName" name="name" required="required" class="form-control"
                           placeholder="run" minlength="2" maxlength="255"
                           value="${model.language???then(model.language.name, "")}">
                </div>

                <div class="form-group">
                    <label for="inputExtensions" class="required">Расширения (через пробел)</label>
                    <input type="text" id="inputExtensions" name="extensions" required="required" class="form-control"
                           maxlength="255" value="${model.language???then(model.language.extensions, "")}">
                </div>

                <div class="form-group">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" id="inputRequireEntryPoint"
                               name="requireEntryPoint" class="custom-control-input"
                                ${model.language???then(model.language.requireEntryPoint???then("checked",""),"")}>
                        <label class="custom-control-label" for="inputRequireEntryPoint">
                            RequireEntryPoint ?
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputEntryPointDescription" class="required">entryPointDescription</label>
                    <input type="text" id="inputEntryPointDescription" name="entryPointDescription"
                           class="form-control"
                           placeholder="run" minlength="2" maxlength="255"
                           value="${model.language???then(model.language.entryPointDescription!, "")}">
                </div>

                <div class="form-group">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" id="inputAllowSubmit"
                               name="allowSubmit" class="custom-control-input"
                                ${model.language???then(model.language.allowSubmit???then("checked",""),"")}>
                        <label class="custom-control-label" for="inputAllowSubmit">
                            Разрешить сдачу ?
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" id="inputAllowJudge"
                               name="allowJudge" class="custom-control-input"
                                ${model.language???then(model.language.allowJudge???then("checked",""),"")}>
                        <label class="custom-control-label" for="inputAllowJudge">
                            Разрешить тестирование ?
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" id="inputFilterCompilerFiles"
                               name="filterCompilerFiles" class="custom-control-input"
                                ${model.language???then(model.language.filterCompilerFiles???then("checked",""),"")}>
                        <label class="custom-control-label" for="inputFilterCompilerFiles">
                            FilterCompilerFiles ?
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label class="" for="inputSpecialCompare">Исполняемый файл компиляции</label>
                    <select id="inputSpecialCompare" name="compileScript"
                            class="form-control custom-select form-control" required>
                        <#if model.specialCompile??>
                            <#list model.specialCompile as compileExecutable>
                                <option value="${compileExecutable.id}"
                                        ${model.language???then((model.language.compileScript == compileExecutable.id)?then("selected",""),"")}>
                                    ${compileExecutable.name}
                                </option>
                            </#list>
                        </#if>
                    </select>
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
