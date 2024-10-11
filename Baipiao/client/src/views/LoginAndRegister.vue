<template>
  <div class="outlayer">
      <div class="container" :class="{ 'create_account': isCreateAccount }">
   <!-- Right -->
          <div class="right_container">
              <div class="slide_adjusted">

                      <!-- Sign In -->
                      <form action="" class="sign_in_section">
                          <h2 class="title">Sign In</h2>
                          <div class="input_section">
                              <i class="fas fa-envelope"></i>
                              <input type="text" v-model="signInModel.email" placeholder=" Email">
                          </div>
                          <div class="input_section">
                              <i class="fas fa-lock"></i>
                              <input type="password" v-model="signInModel.password" placeholder=" Password">
                          </div>
                          <input type="submit" value="Sign In" class="btn solid" v-on:click="signInAccount">
                      </form>

                      <!-- Registeration -->
                      <form action="" class="sign_up_section">
                          <h2 class="title">Create Account</h2>
                          <div class="input_section">
                              <i class="fas fa-user"></i>
                              <input type="text" v-model="member.name" placeholder=" User Name">
                          </div>
                          <div class="input_section">
                              <i class="fas fa-envelope"></i>
                              <input type="text" v-model="member.email" placeholder=" Email">
                          </div>
                          <div class="input_section">
                              <i class="fas fa-lock"></i>
                              <input type="password" v-model="member.password" placeholder=" Password">
                          </div>
                          <div class="input_section">
                              <i class="fas fa-lock"></i>
                              <input type="password" v-model="member.confirmedPassword" placeholder=" Confirm  Password">
                          </div>
                          <div class="input_section">
                              <i class="fas fa-admin"></i>
                              <select name="" id="" v-model="member.identity">
                                  <option value="">Select User</option>
                                  <option value="Admin">Admin</option>
                                  <option value="General">General</option>
                              </select>
                              <!-- <input type="password" placeholder=" Confirm  Password"> -->
                          </div>
                          <input type="submit" value="Sign Up" class="btn solid" v-on:click="signUpAccount">
                      </form>
              </div>
          </div>
  <!-- Left -->
          <div class="left_container">
                  <div class="descript_section">
                      <!-- Descript Section Left -->
                      <div class="descript left_section">
                          <div class="content">
                              <h2>Baipiao</h2>
                              <p>New User? Create an Account?</p>
                              <button class="btn transparent" id="create_account_btn" @click="isCreateAccount = true">Sign Up</button>
                          </div>
                      </div>

                      <!-- Descript Section Right -->
                      <div class="descript right_section">
                          <div class="content">
                              <h2>Baipiao</h2>
                              <p>Existed User? Want to come back?</p>
                              <button class="btn transparent" id="sign_in_btn" @click="isCreateAccount = false">Sign In</button>
                          </div>
                      </div>
                  </div>
          </div>
      </div>
  </div>
  </template>

  <script>
import jwt_decode from "jwt-decode";
  export default{
      name:'LoginAndRegister',
      data(){
          return{
              isCreateAccount: false,
              member:{
                name: '',
                email: '',
                password: '',
                confirmedPassword: '',
                identity: '',
              },
              signInModel:{
                email: '',
                password: '',
              }
          };
      },
      methods:{
          handleCreateAccount(){
              this.isCreateAccount = true;
          },
          handleSignIn(){
              this.isCreateAccount = false;
          },
          async signUpAccount(){
            try {
              // console.log(this.member)
              const newMember = await this.$http.post('/user/register', this.member);
              // console.log(newMember);
              this.handleSignIn()
            } catch (error) {
              console.log(error);
              alert("Please Input an Formula Email")
            }
          },
          signInAccount(){
            try {
              this.$http.post('/user/login', this.signInModel).then(res => {

              //登录成功
              const { token } = res.data;
              localStorage.setItem("eleToken", token);

              // 解析token
              const decode = jwt_decode(token);

              // this.$store.dispatch("setIsAutnenticated", !this.isEmpty(decode));
              // this.$store.dispatch("setUser", decode);
                this.$router.push('/');
              })
            } catch (error) {
              console.log(error);
              alert("Please Input an Formula Email")
            }
          }
      }
  }


  </script>

  <style scoped>

  .outlayer{
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: #bccad1;
      transition: 0.5s;
  }

  .container{
      position: relative;
      width: 800px;
      height: 500px;
      background-color: #fff;
      overflow: hidden;
      box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
      border-radius: 35px;
  }

  .container::before {
      content: "";
      position: absolute;
      width: 2000px;
      height: 2000px;
      top: -10%;
      right: 50%;
      transform: translateY(-50%);
      background-image: linear-gradient(-45deg, #509cf2 0%, #04befe 100%);
      transition: 1.8s ease-in-out;
      /* border-radius: 50%; */
      z-index: 6;
  }

  .right_container{
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
  }

  .slide_adjusted{
      position: absolute;
      top: 50%;
      transform: translate(-50%, -50%);
      left: 75%;
      width: 50%;
      transition: 1s 0.7s ease-in-out;
      display: grid;
      grid-template-columns: 1fr;
      z-index: 5;
  }

  form{
      display: flex;
      align-items: center;
      justify-content: center;
      flex-direction: column;
      padding: 0 5rem;
      overflow: hidden;
      grid-column: 1 / 2;
      grid-row: 1 / 2;
  }

  form.sign_in_section{
      z-index: 2;
  }

  form.sign_up_section{
      z-index: 1;
      /* changing the sign_in or sign_up */
      opacity: 0;
  }

  .title{
     font-size: 1.2rem;
     color: #444;
     margin-bottom: 10px;
  }

  .input_section{
      max-width: 380px;
      width: 100%;
      height: 55px;
      background-color: #f0f0f0;
      margin: 10px 0px;
      border-radius: 55px;
      display: grid;
      grid-template-columns: 15% 85%;
      padding: 0 .4rem;
  }

  .input_section i{
      text-align: center;
      line-height: 55px;
      color: #acacac;
      font-size: 1.1rem;
  }

  .input_section input{
      background: none;
      outline: none;
      border: none;
      line-height: 1;
      font-weight: 600;
      font-size: 1.1rem;
      color: #333;
  }
  .input_section select {
    border: none;
    outline: none;
    background: none;
    color: #aaa;
    font-size: 1.1rem;
    font-weight: 500;
  }


  .input_section::placeholder{
      color: #333;
      font-weight: 500;
  }

  .btn{
      width: 250px;
      height: 49px;
      border:none;
      outline: none;
      border-radius: 49px;
      cursor: pointer;
      background-color: rgba(67, 83, 83, 0.333);
      color:#fff;
      text-transform: uppercase;
      font-weight: 600;
      margin:10px 0;
      transition: .5s;
  }

  .btn:hover{
      background-color: #191a1b;
  }

  .descript_section{
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      display: grid;
      grid-template-columns: repeat(2, 1fr);
  }

  .descript{
      display: flex;
      flex-direction: column;
      align-items: flex-end;
      justify-content: space-around;
      text-align: center;
      z-index: 7;
  }

  .left_section{
      pointer-events: all;
      padding: 3rem 17% 2rem 12%;
  }

  .right_section{
      pointer-events: none;
      padding: 3rem 12% 2rem 17%;
  }

  .descript .content{
      color: #f5f0f0;
      transition: transform 0.9s ease-in-out;
      transition-delay: 0.6s;
  }

  .descript h2 {
      font-weight: 600;
      line-height: 1;
      font-size: 1.5rem;
    }

  .descript p {
      font-size: 0.95rem;
      font-weight: 600;
      padding: 0.7rem 0;
  }

  .btn.transparent {
      margin: 0;
      background: none;
      border: 2px solid #fff;
      width: 130px;
      height: 41px;
      font-weight: 600;
      font-size: 0.8rem;
  }

  .right_section .content{
      transform: translateX(800px);
  }

  /* Animated transfer*/

  .container.create_account:before {
      transform: translate(100%, -50%);
      right: 50%;
    }

    .container.create_account .left_section .content {
      transform: translateX(-800px);
    }

    .container.create_account .slide_adjusted {
      left: 25%;
    }

    .container.create_account form.sign_up_section {
      opacity: 1;
      z-index: 2;
    }

    .container.create_account form.sign_in_section {
      opacity: 0;
      z-index: 1;
    }

    .container.create_account .right_section .content {
      transform: translateX(0%);
    }

    .container.create_account .left_section{
      pointer-events: none;
    }

    .container.create_account .right_section {
      pointer-events: all;
    }

    @media (max-width: 870px) {
      .container {
        min-height: 800px;
        height: 100vh;
        border-radius: 0%;
      }

      .container:before{
        width:1500px;
        height:1500px;
        transform: translateX(-50%);
        left: 30%;
        bottom: 68%;
        right: initial;
        top: initial;
        transition: 2s ease-in-out;
      }

      .slide_adjusted{
        width: 100%;
        left: 50%;
        top: 95%;
        transform: translate(-50%, -100%);
        transition: 1s 0,8 ease-in-out;
      }

      .descript_section {
        grid-template-columns: 1fr;
        grid-template-rows: 1fr 2fr 1fr;
      }

      .descript{
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
        padding: 2.5rem 8%;
      }

      .descript .content{
        transition: 0.9s 0.8s ease-in-out;
        transition-delay: 0.8s;
      }

      .descript h2{
        font-size: 1.2rem;
      }

      .descript p{
        font-size: 0.7rem;
        padding: 0.5rem 0;
      }

      .btn.transparent{
        width: 110px;
        height: 35px;
        font-size: 0.7rem;
      }

      .left_section{
        grid-row: 1 / 2;
      }

      .right_section{
        grid-row: 3 / 4;
      }

      .right_section .content{
        transform: translateY(300px);
    }
      .container.create_account::before{
        transform: translate(-50%, 100%);
        bottom: 30%;
      }

      .container.create_account .left_section,
      .container.create_account .left_section .content{
        transform: translate(-300px);
      }

      .container.create_account .slide_adjusted{
        top: 5%;
        transform: translate(-50%, 0);
        left: 50%;
      }
    }


  @media (max-width: 570px) {
      form {
        padding: 0 1.5rem;
      }

      .descript .content {
        padding: 0.5rem 1rem;
      }
      .container {
        padding: 1.5rem;
      }
      .container:before {
        bottom: 72%;
        left: 50%;
      }

      .container.create_account::before {
        bottom: 28%;
        left: 50%;
      }
    }
  </style>