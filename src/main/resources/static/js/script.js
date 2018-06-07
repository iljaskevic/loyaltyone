$(function() {

  var loyaltyone = {
    getCommentTemplate: function(comment) {
      let dateCreated = new Date(comment.dateCreated);
      let today = new Date();
      let commentDate = dateCreated.toLocaleDateString();
      if (dateCreated.getDate() === today.getDate()
          && dateCreated.getMonth() === today.getMonth()
          && dateCreated.getFullYear() === today.getFullYear()) {

        commentDate = 'Today';
      }
      let commentUser = 'Anonymous';
      if (comment.username) {
        commentUser = comment.username;
      }
      let comTemplate = `<div id="${comment.id}" class="comment">
      <div class="top-info">
        <div class="date-created">${commentDate}, ${dateCreated.toLocaleTimeString()}</div>
        <div class="user">${commentUser}</div>
      </div>
      <div class="content">${comment.content}</div>
      <div class="bottom-info"></div>
      <div class="replies"></div>
      </div>`;
      return comTemplate;
    },

    populateRootComments: function(comments) {
      $('.comments').empty();
      comments.forEach(function(item) {
        let newComment = loyaltyone.getCommentTemplate(item);
        $('.comments').append(newComment);
      });
    },

    loadRootComments: function(isUserFiltered) {
      let url = '/api/comments/';
      if (isUserFiltered) {
        url = url + '?username=' + loyaltyone.username;
      }
      $.ajax({
        type: 'GET',
        url: url,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
          $('textarea#add-comment-text').val('');
          loyaltyone.populateRootComments(data);
        },
        failure: function(errMsg) {
          console.log( errMsg );
        }
      });
    },

    addHandlers: function() {
      $('#add-comment-btn-post').click(function() {
        var comment = {
          content: $('textarea#add-comment-text').val(),
          username: loyaltyone.username
        };

        if (comment.content.trim() === "") return;

        $.ajax({
          type: 'POST',
          url: '/api/comments/',
          data: JSON.stringify(comment),
          contentType: 'application/json',
          dataType: 'json',
          success: function(data){
            var newComment = loyaltyone.getCommentTemplate(data);
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

      $('#user-info-form').submit(function(e) {
        loyaltyone.username = $('#username').val();
        $('.hello-user').html(`Hello <span class="user">${loyaltyone.username}</span>!`);
        $('#init-overlay').hide();
        e.preventDefault();
      });
    },

    filterSelected: function(val) {
      if (val === 'all') {
        loyaltyone.loadRootComments(false);
      } else {
        loyaltyone.loadRootComments(true);
      }
    },

    init: function() {
      loyaltyone.addHandlers();
      loyaltyone.loadRootComments();
    }
  };

  loyaltyone.init();
});