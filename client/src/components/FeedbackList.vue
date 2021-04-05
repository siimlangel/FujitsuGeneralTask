<template>
  <div>
      <table>
          <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Email</th>
                <th>Text</th>
                <th>Category</th>
            </tr>
          </thead>
          <tbody ref="scrolling-table">
           
              <tr class="feedback-list-tr" v-for="feedback in feedbacks" :key="feedback.id">
                  <td>{{feedback.id}}</td>
                  <td>{{feedback.username}}</td>
                  <td>{{feedback.email}}</td>
                  <td>{{feedback.text}}</td>
                  <td class="category-td">
                      <div v-for="cat in feedback.categories" :key="cat.id">
                          {{cat.type}}
                      </div>
                  </td>
              </tr>
          
          </tbody>
      </table>
      <button v-if="!donePaginating" @click="getPaginatedFeedbacks">Load more feedbacks</button>
  </div>
</template>

<script>

import FeedbackDataService from "../services/FeedbackDataService";

export default {
    name: "feedback-list",
    data() {
        return {
            feedbacks: [],
            page: 0,
            size: 5,
            donePaginating: false
        }
    },
    methods: {
        getPaginatedFeedbacks() {
            FeedbackDataService.getPaginated(this.size, this.page)
            .then(res => {
                
                // Add fetched feedbacks to current feedback array
                this.feedbacks = [...this.feedbacks, ...res.data.content];
                

                this.page += 1;

                // If last page of feedbacks reached
                if (res.data.last) {
                    this.donePaginating = true;
                }

            })
            .catch(e => {
                console.log(e);
            })
            
        },
        // Scrolls to the bottom of the feedback table 
        scrollToTableBottom() {
            const scroll = this.$refs["scrolling-table"];
            // Scroll only if this isn't called on the very first updated
            if(scroll.childElementCount != this.size) {
            scroll.scrollTo({top: scroll.scrollHeight, behavior: "smooth"});
        }
        }
    },
    mounted() {
        this.getPaginatedFeedbacks();
    },
    updated() {
        this.scrollToTableBottom();
    }
}
</script>

<style scoped>

table {
    width: 100%;
    border-spacing: 0 2em;
}

.feedback-list-tr {
    padding-top: 20px;
}

.feedback-list-tr td {
    overflow: auto;
}

.category-td {
    display: flex;
    justify-content: space-evenly;
}



tbody {
    display: block;
    height: 250px;
    overflow: auto;
    overscroll-behavior: none;
    border: 1px solid black;
}

thead, tbody tr {
    display: table;
    width: 100%;
    table-layout: fixed;
}

</style>