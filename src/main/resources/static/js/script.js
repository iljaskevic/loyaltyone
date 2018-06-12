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

    let replies = '';
    if (comment.repliesCount > 0) {
      replies = `<a href="#" data-parent-id="${comment.id}" class="show-replies-btn">[+] View Replies (${comment.repliesCount})</a>`;
    }

    let comTemplate = `
    <div id="${comment.id}" class="comment">
      <div class="top-info">
        <div class="user">${comment.user.username}</div>
        <div class="date-created">- ${commentDate}, ${dateCreated.toLocaleTimeString()},</div>
        <div class="city">City: ${comment.locationInfo.city}</div>
        <div class="loc">(${comment.locationInfo.lon},${comment.locationInfo.lat}),</div>
        <div class="temp">Temp: ${Math.round(comment.locationInfo.temp)}&#8451;</div>
      </div>
      <div class="content">
        <div class="content-text">${comment.content}</div>
        <div class="bottom-info">
          <div class="comment-ctl">
            <a href="#" data-parent-id="${comment.id}" class="reply-btn">Reply</a>
            ${replies}
          </div>
        </div>
      </div>
      <div class="replies"></div>
    </div>`;
    return comTemplate;
  },

  getCommentBox: function(parentId) {

    return `<div class="add-reply">
        <div class="input-group mb-3">
          <textarea class="form-control add-reply-text" placeholder="Post something..." aria-label="Post something..."></textarea>
          <div class="input-group-append">
            <button data-parent-id="${parentId}" class="btn btn-outline-primary add-reply-btn-post" type="button">Done</button>
            <button class="btn btn-outline-secondary add-reply-btn-cancel" type="button">Clear</button>
          </div>
        </div>
    </div>`;
  },

  populateRootComments: function(comments) {
    $('.comments').empty();
    comments.forEach(function(item) {
      let newComment = loyaltyone.getCommentTemplate(item);
      $('.comments').append(newComment);
    });
  },

  insertRootComment: function(comment) {
    $('.comments').prepend(loyaltyone.getCommentTemplate(comment));
  },

  populateReplyComments: function(parentId, comments) {
    $(`#${parentId}`).children('.replies').empty();
    comments.forEach(function(item) {
      let newComment = loyaltyone.getCommentTemplate(item);
      $(`#${parentId}`).children('.replies').append(newComment);
    });
  },

  insertReplyComment: function(parentId, comment) {
    $(`#${parentId}`).children('.replies').append(loyaltyone.getCommentTemplate(comment));
  },

  getComments: function(url, successHandler) {

    $.ajax({
      type: 'GET',
      url: url,
      contentType: 'application/json',
      dataType: 'json',
      success: successHandler,
      failure: function(errMsg) {
        console.log( errMsg );
      }
    });
  },

  loadRootComments: function() {
    let url = '/api/comments';

    if (loyaltyone.isUserFiltered) {
      url = url + '?q=' + loyaltyone.user.id;
    }

    loyaltyone.getComments(url, function(data) {
      loyaltyone.populateRootComments(data);
    });
  },

  loadReplyComments: function(parentId) {
    let url = '/api/comments/';

    if (parentId) {
      url = url + `${parentId}/replies`;
    }

    if (loyaltyone.isUserFiltered) {
      url = url + '?q=' + loyaltyone.user.id;
    }

    loyaltyone.getComments(url, function(data) {
      loyaltyone.populateReplyComments(parentId, data);
    });
  },

  postComment: function(comment, successHandler) {
    if (loyaltyone.location) {
      comment.locationInfo = {
        city: loyaltyone.location.city,
        temp: loyaltyone.location.temp,
        lon: loyaltyone.location.coord.lon,
        lat: loyaltyone.location.coord.lat
      };
    }
    $.ajax({
      type: 'POST',
      url: '/api/comments',
      data: JSON.stringify(comment),
      contentType: 'application/json',
      dataType: 'json',
      success: successHandler,
      failure: function(errMsg) {
        console.log( errMsg );
      }
    });
  },

  addHandlers: function() {
    $('#add-comment-btn-post').click(function() {
      var comment = {
        content: $('textarea#add-comment-text').val(),
        user: loyaltyone.user
      };

      if (comment.content.trim() === "") return;

      loyaltyone.postComment(comment, function(data) {
        loyaltyone.insertRootComment(data);
      });

      $('textarea#add-comment-text').val('');
    });

    $('#add-comment-btn-cancel').click(function() {
      $('textarea#add-comment-text').val('');
    });

    $('.comments').on('click', '.show-replies-btn', function(e) {
      console.log('Showing replies');
      $(this).hide();
      loyaltyone.loadReplyComments($(this).data('parent-id'));
      e.preventDefault();
    });

    $('.comments').on('click', '.reply-btn', function(e) {
      console.log('Clicked');
      $(this).parent().hide();
      $(this).parent().parent().parent().siblings('.replies').prepend(loyaltyone.getCommentBox($(this).data('parent-id')));
      e.preventDefault();
    });

    $('.comments').on('click', '.add-reply-btn-post', function(e) {
      console.log('Clicked post');
      var comment = {
        parentId: $(this).data('parent-id'),
        content: $(this).parent().parent().find('.add-reply-text').val(),
        user: loyaltyone.user
      };

      if (comment.content.trim() === "") return;

      loyaltyone.postComment(comment, function(data) {
        loyaltyone.insertReplyComment(comment.parentId, data);
        $(`#${comment.parentId} .comment-ctl`).show();
        $(`#${comment.parentId} .add-reply`).remove();
      });
      e.preventDefault();
    });

    $('.comments').on('click', '.add-reply-btn-cancel', function(e) {
      console.log('Clicked cancel');
      let parentId = $(this).data('parent-id');
      $(`#${parentId} .comment-ctl`).show();
      $(`#${parentId} .add-reply`).remove();
      e.preventDefault();
    });

    $('#user-info-form').submit(function(e) {
      let user = {
        username: $('#username').val()
      };

      loyaltyone.getWeather($('#city').val());

      $.ajax({
        type: 'POST',
        url: '/login',
        data: JSON.stringify(user),
        contentType: 'application/json',
        dataType: 'json',
        success: function(user, status, jqXHR) {
          loyaltyone.user = user;
          let welcome = 'Welcome';
          if (jqXHR.status === 200) {
            welcome = 'Welcome back';
          }
          $('.hello-user .welcome').html(`${welcome}`);
          $('.hello-user .user').html(`${loyaltyone.user.username}`);
          $('.hello-user .temp').hide();
          $('.hello-user .city').hide();
          $('#init-overlay').hide();
        },
        failure: function(errMsg) {
          console.log( errMsg );
        }
      });

      e.preventDefault();
    });
  },

  filterSelected: function(val) {
    if (val === 'all') {
      loyaltyone.isUserFiltered = false;
      loyaltyone.loadRootComments();
    } else {
      loyaltyone.isUserFiltered = true;
      loyaltyone.loadRootComments();
    }
  },

  getWeather: function(city) {
    $.ajax({
      type: 'GET',
      url: `https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=f897089667f54eca680d91247fe53d95&callback=?`,
      contentType: 'application/json',
      dataType: 'jsonp',
      jsonpCallback: 'loyaltyone.weatherCallback'
    });
  },

  weatherCallback: function(data) {
    loyaltyone.location = {
      city: data.name,
      coord: data.coord,
      temp: data.main.temp
    };
    if (loyaltyone.location) {
      $('.hello-user .temp').html(`Temp: ${Math.round(loyaltyone.location.temp)}&#8451;`);
      $('.hello-user .temp').show();
      $('.hello-user .city').html(`City: ${loyaltyone.location.city}`);
      $('.hello-user .city').show();
    }
  },

  init: function() {
    loyaltyone.addHandlers();
    loyaltyone.loadRootComments();
  }
};
$(function() {
  loyaltyone.init();
});