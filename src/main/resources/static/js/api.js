$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/v1/books',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let trHTML = '';
            $.each(data, function (i, item) {
                trHTML = trHTML + '<tr id="book-' + item.id + '">' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.title + '</td>' +
                    '<td>' + item.author + '</td>' +
                    '<td>' + item.price + '</td>' +
                    '<td>' + item.category + '</td>' +
                    '<td colspan="3">' +
                    '<a class="btn btn-primary" ' +
                    'sec:authorize="hasAnyAuthority(\'ADMIN\')" ' +
                    'href="/books/edit/' + item.id + '">Edit</a>' +
                    '<a class="btn btn-danger" href="/books" ' + item.id + '  ' +
                    'onclick="apiDeleteBook(' + item.id + ', this); return false;" ' +
                    'sec:authorize="hasAnyAuthority(\'ADMIN\')">Delete</a>' +
                    '<form th:action="@{/books/add-to-cart}" method="post" class="d-inline">' +
                    '<input type="hidden" name="id" value="' + item.id + '"/>' +
                    '<input type="hidden" name="name" value="' + item.title + '"/>' +
                    '<input type="hidden" name="price" value="' + item.price + '"/>' +
                    '<button type="submit" class="btn btn-success" ' +
                    'onclick="return confirm(\'Are you sure you want to add this book to cart?\')">Add to cart</button>' +
                    '</td>' +
                    '</tr>';
            });
            $('#book-table-body').append(trHTML);
        }
    });
});

function apiDeleteBook(id) {
    if (confirm('Are you sure you want to delete this book?')) {
        $.ajax({
            url: 'http://localhost:8080/api/v1/books/' + id,
            type: 'DELETE',
            success: function (result) {
                alert('Book deleted successfully')
                $('#book-' + id).remove();
            }
        });
    }
}