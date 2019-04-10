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
                <div class="form-group">
                    <label for="inputName">Введите название задачи</label>
                    <input name="name" type="text" class="form-control" id="inputName" placeholder="A + B" minlength="3"
                           maxlength="128">
                </div>

                <div class="form-group">
                    <label for="inputMaxTime">Ограничение времени в мс</label>
                    <input name="maxTime" type="number" class="form-control" id="inputMaxTime" min="1" value="1">
                </div>

                <div class="form-group">
                    <label for="inputMaxRealTime">Ограничение реального времени в секундах</label>
                    <input name="maxRealTime" type="number" class="form-control" id="inputMaxRealTime" min="1" value="1">
                </div>

                <div class="form-group">
                    <label for="inputMaxMemory">Ограничение по памяти в МБ</label>
                    <input name="maxMemory" type="number" class="form-control" id="inputMaxMemory" min="1" value="1">
                </div>

                <div class="form-group">
                    <label for="inputComplexity">Сложность</label>
                    <input name="complexity" type="number" class="form-control" id="inputComplexity" min="1" value="1">
                </div>

                <div class="form-group">
                    <label for="inputDescription">Описание задачи</label>
                    <textarea name="description" rows="5" class="form-control" id="inputDescription"
                              maxlength="512"></textarea>
                </div>

                <div class="form-group">
                    <label for="inputInputDescription">Описание входных данных</label>
                    <textarea name="inputDescription" rows="5" class="form-control" id="inputInputDescription"
                              maxlength="512"></textarea>
                </div>

                <div class="form-group">
                    <label for="inputOutputDescription">Описание выходных данных</label>
                    <textarea name="outputDescription" rows="5" class="form-control" id="inputOutputDescription"
                              maxlength="512"></textarea>
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
