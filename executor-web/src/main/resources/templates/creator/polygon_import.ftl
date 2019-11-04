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
            <form action="import" enctype="multipart/form-data" method='POST'>
                <div class="form-group">
                    <#if error??>
                            <div class="alert alert-danger" role="alert">${error}</div>
                    </#if>
                </div>
                <div class="form-group">
                    <#if info??>
                        <div class="alert alert-success" role="alert">${info}</div>
                    </#if>
                </div>
                <div class="form-group">
                    <label for="inputName">Выберите файл для импорта задачи</label>
                    <input name="file" type="file" accept=".zip"
                           class="form-control"
                           id="inputName">
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
