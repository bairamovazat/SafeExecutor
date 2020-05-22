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
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <h5 class="card-title text-center">${model.problem.name}</h5>
                        </li>
                        <li class="list-group-item text-center">
                            Время: ${model.problem.timeLimit???then(model.problem.timeLimit, 0) } сек.
                            Память ${model.problem.memLimit!} МБ.
<#--                            Сложность ${model.problem.complexity!}-->
                        </li>
                        <#if model.user.isPresent() && model.user.get().hasRole("CREATOR")>
                           <li class="list-group-item">
                               <a href="create?taskId=${model.problem.id}">Изменить</a>
                           </li>
                           <li class="list-group-item">
                               <a href="${model.problem.id}/test/all">Тесты</a>
                           </li>
                        </#if>

                        <li class="list-group-item">
                            <h6 class="card-subtitle mb-2">Описание</h6>
                        ${model.problem.description!}
                        </li>
                        <li class="list-group-item">
                            <h6 class="card-subtitle mb-2">Входные данные</h6>
                        ${model.problem.inputDescription!}
                        </li>

                        <li class="list-group-item">
                            <h6 class="card-subtitle mb-2">Выходные данные</h6>
                        ${model.problem.outputDescription!}
                        </li>
<#--                        <#if model.problem.samples??>-->
<#--                            <#list model.problem.samples as sample>-->
<#--                                <li class="list-group-item">-->
<#--                                    <h6 class="card-subtitle mb-2">Пример входных данных: </h6>-->
<#--                                    <div>-->
<#--                                        ${sample.inputData!}-->
<#--                                    </div>-->
<#--                                </li>-->
<#--                                <li class="list-group-item">-->
<#--                                    <h6 class="card-subtitle mb-2">Пример выходных данных: </h6>-->
<#--                                    <div>-->
<#--                                        ${sample.outputData!}-->
<#--                                    </div>-->
<#--                                </li>-->
<#--                            </#list>-->
<#--                        </#if>-->
                        <#if model.user??>
                            <li class="list-group-item">
                                <form action="" method='POST' onsubmit="updateSource()">
                                    <div class="form-group">
                                        <#if errors??>
                                            <#list errors as e>
                                                <div class="alert alert-danger" role="alert">${e}</div>
                                            </#list>
                                        </#if>
                                    </div>
                                    <input name="id" style="display: none;" value="${model.problem.id}">
                                    <div class="form-group">
                                        <label for="inputText">Код</label>
                                        <textarea id="codeImport" name="codeImport" rows="1" class="form-control"
                                                  maxlength="2048">${"import java.util.ArrayList;"}</textarea>
                                        <textarea id="programHeader" class="code_not_resize form-control" content="" rows="1"
                                                  disabled>${"public class Program {"}</textarea>
                                        <textarea id="code" style="overflow:hidden" name="code" rows="3" class="form-control"
                                                  maxlength="2048"
                                                  content="">${"    public static void main(String[] args){\n\n    }"}</textarea>
                                        <textarea id="programFooter" class="code_not_resize form-control" content="" rows="1"
                                                  disabled>${"}"}</textarea>
                                        <input id="source" style="display: none" name="source">
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" type="submit" value="Отправить">
                                    </div>
                                </form>
                            </li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
    </div>
</div>
<script>
    $('textarea').autoResize();
    function updateSource() {
        let codeImport = document.getElementById("codeImport");
        let programHeader = document.getElementById("programHeader");
        let code = document.getElementById("code");
        let programFooter = document.getElementById("programFooter");

        let source = document.getElementById("source");
        source.value = codeImport.value + "\n" + programHeader.value + "\n" + code.value + "\n" + programFooter.value + "\n"
    }
</script>
<style>
    .code_not_resize {
        resize: none;
    }
</style>

<#include "../footer.ftl">
</body>
</html>
