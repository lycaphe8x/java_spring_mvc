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
                        '<td>' + item.categoryName + '</td>' +
                        '<td sec:authorize="hasAnyAuthority(\'ADMIN\')">' +
                            '<a href="#' + item.id + '" class="text-primary">Edit</a> | ' +
                            '<a href="/books"'+ item.id +' class="text-danger" onclick="apiDeleteBook(' + item.id + ', this); return false;">Delete</a>' +
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
            success: function () {
                alert('Book deleted successfully!');
                $('#book-' + id).remove();
            }
        });
    }
}