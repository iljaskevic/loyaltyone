$(function() {

  var loyaltyone = {
    getCommentTemplate: function(text) {
      var comTemplate = `<div class="comment">
      <div class="content">${text}</div>
      <div class="replies"></div>
      </div>`;
      return comTemplate;
    },

    loadRootComments: function() {
      $.ajax({
        type: 'GET',
        url: '/api/comments/',
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
          data.forEach(function(item) {
            let newComment = loyaltyone.getCommentTemplate(data.content);
            $('.comments').append(newComment);
            $('textarea#add-comment-text').val('');
          });
        },
        failure: function(errMsg) {
          console.log( errMsg );
        }
      });
    },

    addHandlers: function() {
      $('#add-comment-btn-post').click(function() {
        var comment = {
          content: $('textarea#add-comment-text').val()
        };

        if (comment.content.trim() === "") return;

        $.ajax({
          type: 'POST',
          url: '/api/comments/',
          data: JSON.stringify(comment),
          contentType: 'application/json',
          dataType: 'json',
          success: function(data){
            var newComment = loyaltyone.getCommentTemplate(data.content);
            $('.comments').prepend(newComment);
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
    },

    init: function() {
      loyaltyone.addHandlers();
      loyaltyone.loadRootComments();
    }
  };

  loyaltyone.init();
});