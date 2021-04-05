<template>
  <div>
      <div>
          <h1>Please give us feedback</h1>
      </div>
      <form @submit.prevent="submit">
          <div class="form-input-container">
            <input name="username" v-model="username" required type="text" placeholder="Name">
          </div>
          <div class="form-input-container">
            <input name="email" v-model="email" type="email" placeholder="Email address" required>
          </div>
          <div class="form-input-container">
            <textarea name="text" required minlength="10" maxlength="200" v-model="text" type="text-field" placeholder="Text">
            </textarea>
          </div>

          <label for="">Ctrl-click to select more than one</label>
          <div class="form-input-container">
            <select required v-model="selectedCategories" multiple name="" id="">
                <option 
                v-for="cat in categories"
                :key="cat.id"
                v-bind:value="cat.type"
                >
                {{ cat.type }}
                </option>
            </select>
          </div>

          <input type="submit" value="Send">
      </form>
  </div>
</template>

<script>
import CategoryDataService from "../services/CategoryDataService";
import FeedbackDataService from "../services/FeedbackDataService";


export default {
    name:"send-feedback",
    data() {
        return {
            username: "",
            email: "",
            text: "",
            categories: [],
            selectedCategories: [],
            
        }
    },
    methods: {
        submit() {
    
            const feedback = {
                username:this.username,
                email: this.email,
                text: this.text,
                categories: this.selectedCategories.map(cat => {
                    return {
                        type: cat,
                    }
                }),
            }
            

            
            FeedbackDataService.saveOne(feedback)
            .then(() => {
                window.location.reload();
            })
            .catch(err => {
                console.log(err.response);
            });
            
            
        },
        
        getAllCategories() {
            CategoryDataService.getAll()
            .then(res => {
                this.categories = res.data;
            })
            .catch(err => {
                console.log(err);
            })
        }
    },
    mounted() {
        this.getAllCategories();
    },
}
</script>

<style scoped>
    form{
        display: flex;
        flex-direction: column;
        width: 40%;
        margin-left: auto;
        margin-right: auto;
        height: 600px;
        margin-bottom: 100px;
    }


    textarea {
        resize: vertical;
        min-height: 50px;
    }

    .form-input-container {
        width: 100%;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .form-input-container input {
        
        height: 50%;
        font-size: 20px;
        padding: 10px;
    }

    .form-input-container textarea {
        min-height: 40px;
        max-height: 300px;
        overflow: auto;
        font-size: 20px;
        padding: 10px;
        
    }

    .form-input-container select {
        width: 100%;
        height: 200px;
        font-size: 20px;
    }

    input[type="submit"] {
        background-color: rgb(19, 94, 255);
        border-radius: 5px;
        border: none;
        color: white;
        font-size: 18px;
        height: 40px;
        cursor: pointer;
        transition: background-color 250ms;
    }

    input[type="submit"]:hover {
        background-color: rgb(2, 70, 216);;
    }

    

</style>

