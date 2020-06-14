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
            <#if model.problem??>
                Изменение задачи "${model.problem.name}"
            <#else>
                Создание новой задачи
            </#if>
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
                <input style="display: none;" name="id" value="${model.problem???then(model.problem.id, "")}">

                <div class="form-group">
                    <label for="inputName" class="required">Введите название задачи</label>
                    <input type="text" id="inputName" name="name" required="required" class="form-control"
                           placeholder="A + B" minlength="2" maxlength="128"
                           value="${model.problem???then(model.problem.name, "")}">
                </div>

                <div class="form-group">
                    <label for="inputTimelimit" class="required">Ограничение времени</label>
                    <div class="input-group">
                        <input type="number" id="inputTimelimit" name="timeLimit" required="required" class="form-control"
                               min="0" value="${model.problem???then(model.problem.timeLimit, "")}">
                        <div class="input-group-append">
                            <div class="input-group-text">сек</div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputMemlimit">Ограничение памяти</label>
                    <div class="input-group">
                        <input type="number" id="inputMemlimit" name="memLimit" class="form-control"
                               min="0" value="${model.problem???then(model.problem.memLimit?c, "")}">
                        <div class="input-group-append">
                            <div class="input-group-text">kB</div>
                        </div>
                    </div>
                    <small id="problem_memlimit_help" class="form-text text-muted">Оставьте пустым для значения по
                        умолчанию</small>
                </div>

                <div class="form-group">
                    <label for="inputOutputlimit">Ограничения выходного файла</label>
                    <div class="input-group">
                        <input type="number" id="inputOutputlimit" name="outputLimit" class="form-control"
                               min="0" value="${model.problem???then(model.problem.outputLimit?c, "")}">
                        <div class="input-group-append">
                            <div class="input-group-text">kB</div>
                        </div>
                    </div>
                    <small id="problem_outputlimit_help" class="form-text text-muted">Оставьте пустым для значения по
                        умолчанию</small>
                </div>

                <div class="form-group">
                    <label for="inputDescription">Описание задачи</label>
                    <textarea name="description" rows="5" class="form-control" id="inputDescription"
                              maxlength="1024">${model.problem???then(model.problem.description, "")}</textarea>
                </div>

                <div class="form-group">
                    <label class="" for="inputSpecialRun">Run script</label>
                    <select id="inputSpecialRun" name="specialRun"
                            class="form-control custom-select form-control">
                        <option value="">-- default run script --</option>
                        <#if specialRun??>
                            <#list specialRun as runExecutable>
                                <option value="${runExecutable.id}"
                                        ${model.problem???then((model.problem.specialRun==runExecutable)?then("selected",""),"")}>
                                    ${runExecutable.name}
                                </option>
                            </#list>
                        </#if>
                    </select>
                </div>

                <div class="form-group">
                    <label class="" for="inputSpecialCompare">Compare script</label>
                    <select id="inputSpecialCompare" name="specialCompare"
                            class="form-control custom-select form-control">
                        <option value="">-- default compare script --</option>
                        <#if specialCompare??>
                            <#list specialCompare as compareExecutable>
                                <option value="${compareExecutable.id}"
                                        ${model.problem???then((model.problem.specialCompare==compareExecutable)?then("selected",""),"")}>
                                    ${compareExecutable.name}
                                </option>
                            </#list>
                        </#if>
                    </select>
                </div>

                <div class="form-group">
                    <label for="inputSpecialCompareArgs">Compare script arguments</label>
                    <input type="text" id="inputSpecialCompareArgs" name="specialCompareArgs" class="form-control"
                           value="${model.problem???then(model.problem.specialCompareArgs, "")}">
                </div>


                <div class="form-group">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" id="inputCombinedRunCompare"
                               name="combinedRunCompare" class="custom-control-input"
                                ${model.problem???then(model.problem.combinedRunCompare???then("checked",""),"")} >
                        <label class="custom-control-label" for="inputCombinedRunCompare">
                            Use run script as compare script.
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputInputDescription">Описание входных данных</label>
                    <textarea name="inputDescription" rows="5" class="form-control" id="inputInputDescription"
                              maxlength="512">${model.problem???then(model.problem.inputDescription, "")}</textarea>
                </div>

                <div class="form-group">
                    <label for="inputOutputDescription">Описание выходных данных</label>
                    <textarea name="outputDescription" rows="5" class="form-control" id="inputOutputDescription"
                              maxlength="512">${model.problem???then(model.problem.outputDescription, "")}</textarea>
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
