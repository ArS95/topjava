// $(document).ready(function () {
var ajaxMealUrl = "ajax/profile/meals/";
$(function () {
    makeEditable({
            ajaxUrl: ajaxMealUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

function filter() {
    $.ajax({
        type: "GET",
        url: ajaxMealUrl + "filter"
    }).done(function () {

        updateTable();
        // updateTableByFilter();
        successNoty("Filtered");
    })
}