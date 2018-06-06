$(function() {

  function getCommentTemplate(text) {
    var comTemplate = `<div class="comment">
    <div class="content">${text}</div>
    <div class="replies"></div>
    </div>`;
    return comTemplate;
  }

  $('#add-comment-btn-post').click(function() {
    var comment = {
      content: $('textarea#add-comment-text').val()
    };

    if (comment.content.trim() === "") return;

    $.ajax({
      type: 'POST',
      url: '/api/comments',
      data: JSON.stringify(comment),
      contentType: 'application/json',
      dataType: 'json',
      success: function(data){
        var newComment = getCommentTemplate(data.content);
        $('.comments').append(newComment);
        $('textarea#add-comment-text').val('');
      },
      failure: function(errMsg) {
        console.log( errMsg );
      }
    });
  });
  $('#add-comment-btn-cancel').click(function() {
    $('textarea#add-comment-text').val('');
  });

});