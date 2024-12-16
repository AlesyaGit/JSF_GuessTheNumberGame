<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Guess the Number Game</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        
        .modal {
            display: none; 
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            text-align: center;
            border-radius: 8px;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        
        .used-numbers {
            margin-top: 20px;
            text-align: left;
        }
        .used-numbers ul {
            list-style-type: none;
            padding: 0;
        }
        .used-numbers li {
            font-size: 16px;
            color: #555;
        }
    </style>
</head>
<body>
    <div class="game-container">
        <h1 class="turns">Turns: <c:out value="${sessionScope.attempts != null ? sessionScope.attempts : 0}"/></h1>
        <div class="number-display">
            <div class="range">1</div>
            <div class="question-mark">?</div>
            <div class="range">100</div>
        </div>
        <p class="instruction">Start guessing a number...</p>

        <div class="message">
            <c:if test="${not empty message}">
                <p>${message}</p>
            </c:if>
        </div>

        <form method="post" action="guess">
            <div class="input-container">
                <input type="number" id="guess" name="guess" required min="1" max="100" class="number-input" placeholder="Enter a number">
            </div>
            <div class="buttons-container">
                <button type="submit" class="btn guess">Guess</button>
                <button type="button" class="btn clear" onclick="document.getElementById('guess').value=''">Clear</button>
                <button id="hintButton" type="button" class="btn btn-primary" onclick="openHintModal()" disabled>Hint</button>
            </div>
        </form>

        
        <div class="used-numbers">
            <h3>Used Numbers:</h3>
            <ul>
                <c:forEach var="num" items="${sessionScope.usedNumbers}">
                    <li>${num}</li>
                </c:forEach>
            </ul>
        </div>

        
        <div id="hintModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeHintModal()">&times;</span>
                <p id="hintMessage">${sessionScope.hintMessage}</p>
            </div>
        </div>
    </div>

    <script>
        
        function openHintModal() {
            const xhr = new XMLHttpRequest();
            xhr.open("GET", "hint", true);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    document.getElementById("hintMessage").innerText = xhr.responseText;
                    document.getElementById("hintModal").style.display = "block";
                }
            };
            xhr.send();
        }

        
        function closeHintModal() {
            document.getElementById("hintModal").style.display = "none";
        }

        
        function updateHintButton() {
            const attempts = parseInt('${sessionScope.attempts != null ? sessionScope.attempts : 0}');
            const hintButton = document.getElementById("hintButton");

            if (attempts >= 2) {
                hintButton.disabled = false;
                hintButton.style.backgroundColor = "#007BFF"; 
            }
        }

        
        window.onclick = function (event) {
            const modal = document.getElementById("hintModal");
            if (event.target === modal) {
                modal.style.display = "none";
            }
        };

        
        document.addEventListener("DOMContentLoaded", updateHintButton);
    </script>
</body>
</html>







