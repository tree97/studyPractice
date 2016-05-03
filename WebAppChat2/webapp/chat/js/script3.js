/**Use ajax
 * Modified by Vadim 26.04.2016
 */

var Application = {
    mainUrl: 'http://localhost:8080/chat',
    messageList: [],
    token: 'TN11EN'
};

function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();

    return Math.floor(date * random);
}

function getTime() {
    var d = new Date();

    return d.toLocaleTimeString();
}

function newMessage(author, text, time, deleted, edited, wasEdited) {
    return {
        author: '' + author,
        text: '' + text,
        timestamp: '' + time,
        deleted: !!deleted,
        edited: !!edited,
        wasEdited: !!wasEdited,
        id: uniqueId()
    };
}

var author = "Untitled";
function run() {
    var appContainer = document.getElementsByClassName('chatContainer')[0];

    appContainer.addEventListener('click', delegateEvent);

    loadMessages(function () {
        render(Application);
    });

    setAuthor();
}

function setAuthor() {
    if (author === null)
        author = "Untitled";

    var inputName = document.getElementById('author');
    inputName.setAttribute('placeholder', author);

    var inputMessage = document.getElementById('message');
    inputMessage.setAttribute('placeholder', author + ', enter you message');
}


function delegateEvent(evtObj) {
    if (evtObj.type === 'click' && evtObj.target.classList.contains('buttonOk')) {
        onOkButtonClick(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('buttonSend')) {
        onSendButtonClick(evtObj);
    }
}

function onOkButtonClick() {
    var nameItem = document.getElementById('author');
    if (!nameItem.value)
        return;
    author = nameItem.value;

    nameItem.style.color = 'red';

    var inputMessage = document.getElementById('message');
    inputMessage.setAttribute('placeholder', author + ', enter you message');
}

function editMessage(element, done) {
    var id = idFromElement(element);

    var index = indexById(Application.messageList, id);

    var message = Application.messageList[index];
    var dialog = document.getElementById("overlay");
    dialog.style.visibility = "visible";

    var inputNewMessage = document.getElementById('newText');
    inputNewMessage.value=message.text;
    inputNewMessage.focus();

    document.getElementById('exit').onclick = function () {
        inputNewMessage.value = "";
        dialog.style.visibility = "hidden";
    };

    document.getElementById('ok').onclick = function () {
        message.text = inputNewMessage.value;
        message.edited = 'true';

        inputNewMessage.value = "";
        dialog.style.visibility = "hidden";

        if (message.wasEdited == 'true') {
            element.removeChild(element.lastChild);
        } 
        ajax('PUT', Application.mainUrl, JSON.stringify(message), function () {
            done(element, message);
        });
    }
}

function deleteMessage(id, done) {
    var url = Application.mainUrl + '?msgId=' + id;

    ajax('DELETE', url, null, function () {
        done();
    });
}

function indexById(list, id) {
    return list.findIndex(function (item) {
        return item.id == id;
    });
}

function onItemClickToDelete(element) {
    var currentElement = element.parentNode;
    var id = idFromElement(currentElement);

    var index = indexById(Application.messageList, id);

    var message = Application.messageList[index];

    message.deleted = 'true';
    deleteMessage(id, function () {
        renderMessageState(currentElement, message);
    });
}

function idFromElement(element) {
    return element.attributes['data-message-id'].value;
}

function onItemClickToEdit(element) {
    var currentElement = element.parentNode;

    var id = idFromElement(currentElement);

    var index = indexById(Application.messageList, id);
    var message = Application.messageList[index];

    if (message.deleted == 'true') {
        return;
    }
    message.edited = 'true';
    editMessage(currentElement, function () {
        renderMessageState(currentElement, message);
    });
}

function loadMessages(done) {
    var url = Application.mainUrl + '?token=' + Application.token;
    ajax('GET', url, null, function (responseText) {
        var response = JSON.parse(responseText);

        Application.messageList = response.messages;
        Application.token = response.token;
        done();
    });
}


function ajax(method, url, data, continueWith) {

    setTimeout(loop, 1000, method, url, data, continueWith);
    function loop(method2, url2, data2, continueWith2)
    {
       var xhr = new XMLHttpRequest();
	 if(method2 !== null)
       {
   		 xhr.open(method2, url2, true);

    		 xhr.onload = function () {
    		    if (xhr.readyState != 4) {
    	 	       return;
    		    }
     		    if (xhr.status != 200) {
     	 	       return;
       	    }
       	    if (isError(xhr.responseText)) {
       	       return;
       	    }
       	    continueWith2(xhr.responseText);
     		 };

     		 var errorIcon = document.getElementById('errorIcon');

     		 errorIcon.style.visibility = "hidden";

    	 	 xhr.onerror = function (e) {
     		    errorIcon.style.visibility = "visible";
     		 };

     		 xhr.send(data2);
       }
    	 method2=null;
	 setTimeout(loop, 2000, method2, url2, data2, continueWith2);
    }
}

function isError(text) {
    if (text == "")
        return false;

    try {
        var obj = JSON.parse(text);
    } catch (ex) {
        return true;
    }

    return !!obj.error;
}

function onSendButtonClick() {
    var textMessage = textValue();
    if (textMessage == "" || textMessage == null)
        return;

    var message = newMessage(author, textMessage, getTime(), false, false, false);

    addMessage(message, function () {
        renderMessage(message);
    });
}

function addMessage(message, done) {
    ajax('POST', Application.mainUrl, JSON.stringify(message), function () {
        Application.messageList.push(message);
        done();
    });
}

function textValue() {
    var messageTextElement = document.getElementById('message');
    var messageTextValue = messageTextElement.value;

    messageTextElement.value = '';

    return messageTextValue;
}

function renderMessage(message) {
    var list = document.getElementsByClassName('listMessage')[0];

    var child = elementFromTemplate('messageTemplate');
    renderMessageState(child, message);
    list.appendChild(child);
}

function render(root) {
    for (var i = 0; i < root.messageList.length; i++) {
        renderMessage(root.messageList[i]);
    }
}

function caseDeletedMessage(element, message) {
    if (message.edited == 'true') {
        element.removeChild(element.lastChild);
    }
    element.firstChild.textContent = "[" + message.timestamp + "]" + " " + message.author + " ";
    element.lastChild.textContent = "";
    element.appendChild(createIcon('http://icons.iconarchive.com/icons/icons8/ios7/256/Messaging-Trash-icon.png'));
}

function renderMessageState(element, message) {
    element.setAttribute('data-message-id', message.id);

    if (message.deleted == 'true') {
        caseDeletedMessage(element, message);
    }
    else {
        element.firstChild.textContent = "[" + message.timestamp + "]" + " " + message.author + " ";
        element.lastChild.textContent = message.text + " ";

        if (message.edited == 'true') {
            element.appendChild(createIcon('http://www.free-icons-download.net/images/edit-icon-61879.png'));
            message.wasEdited = 'true';
        }
    }
}


function createIcon(src) {
    var icon = document.createElement('img');

    icon.setAttribute('src', src);
    icon.setAttribute('width', '20');
    icon.setAttribute('height', '20');

    return icon;
}

function elementFromTemplate(str) {
    var template = document.getElementById(str);

    return template.firstElementChild.cloneNode(true);
}


