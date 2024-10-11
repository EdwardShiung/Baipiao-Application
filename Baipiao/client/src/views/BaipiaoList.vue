<template>
<body>

<div class="home">
    <div class="todo-head">
        <header class="text head-title">Provide Baipiao List</header>
        <div class="inputDiv">
            <input type="text" class="userInput" placeholder="Provide Baipiao?">
            <input class="calender" type="date" placeholder="none" required>
            <span class="addButton">Add</span>
        </div>
    </div>
    <div class="todo-body">
        <ul id="myUL"  v-for="(event, index) in events" :key="event.id">
            <div class="task-bar">
                <li>{{ event.name }}</li>
                <span class="task-icon"></span>
            </div>
          </ul>
    </div>
</div>

<!-- <script src="./js/main.js"></script> -->

</body>
</template>
<script>

export default{
    name:'baipiaolist',

    data() {
      return {
          statsCards: [
          ],
          events: [], // To store the fetched events
      };
  },
    methods: {
      async fetchData() {
          try {
              const response = await axios.get("events");
              // Assuming the API returns an array of event objects
              this.events = response.data; 
          } catch (error) {
              console.error("Error fetching events:", error);
          }
      },
  },
  created() {
    // Fetch events when the component is created
    this.fetchData(); 
  },
};

</script>
<style scoped>

/* ===== Todo List Only ===== */

.home{
    background-color: #f4f3ef;
    /* position: absolute; */
    top: 0;
    top: 0;
    /* left: 250px; */
    height: 100%;
    width: 100%;
    /* width: calc(100% - 250px); */

    transition: all 0.4s ease;
}

.home .text{
    font-size: 35px;
    font-weight: 500;
    color: #707070;
    padding: 12px 60px;
}

.todo-head {
  padding: 30px 40px;
  color: white;
  text-align: center;
}

.userInput{
    margin: 0;
    border-radius: 6px;
    background-color: #f4f3ef;
    border: 2px solid rgb(57, 151, 183);
    outline: none;
    width: 65%;
    height: 65px;
    padding: 10px;
    font-size: 20px;
}

.calender{
    background-color: #f4f3ef;
    padding: 10px;
    width: 140px;
    border: none;
    outline: none;
    border-radius: 6px;
    font-size: 16px;
}

.addButton {
    background-color: #acacac;
    border: none;
    border-radius: 6px;
    color: Black;
    padding: 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
}

.addButton:hover {
    box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
}

#myUL{
    margin: 15px;
    padding: 15px;
}

.task-bar{
  display: flex;
  flex-direction: row;
}

.task-bar li {
    margin: 20px 20px 20px 100px;
    width: 85%;
    border-radius: 8px;
    cursor: pointer;
    position: relative;
    padding: 12px 8px 12px 40px;
    list-style-type: none;
    background: #f19494;
    font-size: 25px;
    text-align: left;
    transition: 0.2s;

    /* make the list items unselectable */
    /* -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none; */
    user-select: none;
}

.task-icon{
    margin: 28px 60px 25px -100px;
    padding: 10px;
    width: 40px;
    height: 40px;
    border-radius: 8px;
    font-size: 35px;
}

.task-icon:hover{
    opacity: 0.5;
}

#myUL li:hover {
    opacity: 0.75;
}

#myUL li.checked {
    background: #a4e886;
    color: #fff;
    text-decoration: line-through;
}

#myUL li.checked::before {
    content: '';
    position: absolute;
    border-color: #fff;
    border-style: solid;
    border-width: 0 2px 2px 0;
    top: 26px;
    left: 16px;
    transform: rotate(45deg);
    height: 15px;
    width: 8px;
}

.todo-head:after {
  content: "";
  display: table;
  clear: both;
}

/* Set the RWD here for three different size --- Pre 870 570 --- */
@media (max-width: 570px) {
    .inputDiv{
        display: flex;
        flex-direction: column;
    }

    .userInput{
        width: 100%;
        height: 50px;
        font-size: 10px;
    }

    .text.head-title{
        font-size: 25px;
    }

    .calender{
        background-color: #f4f3ef;
        margin: auto;
        display: block;
    }

    .addButton {
        margin: auto;
        display: block;
        width: 60px;
    }

    #myUL{
        margin: 0px;
        padding: 0px;
    }

    .task-bar li {
        margin: 20px 20px 20px 20px;
        width: 100%;
        padding: 12px 40px;
        font-size: 15px;
        text-align: left;
        /* make the list items unselectable */
        user-select: none;
    }

    .task-icon{
        margin: 28px 60px 25px -80px;
        padding: 10px;
        width: 40px;
        height: 40px;
        font-size: 30px;
    }
}

@media (min-width: 570px) and (max-width: 870px){


    .userInput{
        width: 80%;
        height: 55px;
        font-size: 15px;
    }

    .text.head-title{
        font-size: 30px;
    }

    .addButton {
        margin: auto;
        display: block;
        width: 60px;
    }

    #myUL{
        margin: 0px;
        padding: 0px;
    }

    .task-bar li {
        margin: 20px 20px 20px 20px;
        width: 100%;
        padding: 12px 40px;
        font-size: 20px;
        text-align: left;
        /* make the list items unselectable */
        user-select: none;
    }

    .task-icon{
        margin: 28px 60px 25px -80px;
        padding: 10px;
        width: 40px;
        height: 40px;
        font-size: 30px;
    }
}


</style>