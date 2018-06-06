$(function() {

  var loyaltyone = {
    getCommentTemplate: function(comment) {
      var comTemplate = `<div id="${comment.id}" class="comment">
      <div class="content">${comment.content}</div>
      <div class="info"><div class="date-created">${comment.dateCreated}</div></div>
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
            let newComment = loyaltyone.getCommentTemplate(item);
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