<template>
    <div class="container">
        <!-- <h1>Email</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Edit email template for the two types of email</p>
        <p>2. Settings for automated email</p> -->
        <h1>Email</h1>
        <h2>Email Types and Templates</h2>
        <div class="container">
          <div class="row row-cols-auto g-5 d-flex justify-content-around">
            <div v-for="template in EmailTemplates" :key="template.templateName" >
                <div class="card" style="width: 30rem;">
                  <div class="card-body">
                    <h5 class="card-title">{{template.templateName}}</h5>
                    <p class="card-text break-text">{{template.templateDraft}}</p>
                    <!-- Update button -->
                    <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#emailModal">
                      Update
                    </button> 
                    <!-- Update modal -->
                    <div class="modal fade" id="emailModal" tabindex="-1"  aria-hidden="true">
                        <div class="modal-dialog modal-xl">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Update {{template.templateName}} information</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                   
                                </div>
                                <div class="modal-footer">
                                    <!-- <button type="button" class="btn btn-warning" data-bs-dismiss="modal" @click="saveContent()">Save</button> -->
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>
                </div>
            </div>

          </div>
        </div>

        <hr>
        <hr>


        
          


        

        
        
          
    </div>
</template>

<script>

export default {
  name: 'Email_tinymce',
  components: {
    
  },
  data(){
    return {
    EmailTemplates: [],
    content_1: "Content 1",
    content_2: "Content 2",
    // custom toolbar
    }
  },
  created(){
      this.EmailTemplates = [
          {
            "templateName": "ePass",
            "templateDraft": `Dear borrower’s name,
                      We are pleased to inform that your booking to attraction name is confirmed as follows: 
                      Date of Visit
                      : Saturday, 12 February 2022 (1 day only)
                      Membership ID
                      : corp pass number /n

                      
                      For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system. 
                      Attached is the Corporate Membership letter to attraction name. Please check that the details are accurate. 
                      Please take note of the following on the day of your visit to attraction name:
                      Present this email, the attached corporate membership letter and your staff pass at the entrance of attraction name. 
                      Entry is strictly based on your details in this email and corporate membership letter.
                      Your presence is required on the day of visit. Entry will be denied without staff’s presence. 
                      Your booking is non-transferable. Entry is strictly based on the details in this email and Corporate Membership letter. 
                      Visit date is strictly based on the date stated in this email and Corporate Membership letter. 
                      Staff found abusing the Corporate Membership letter will be subject to disciplinary actions. 
                      Enjoy your visit to attraction name! `,
              
          },
          { 
            "templateName": "physicalPass",
            "templateDraft": `Dear <borrower’s name>,
                      We are pleased to inform that your booking to <attraction name> is confirmed as follows: 
                      Date of Visit
                      : Saturday, 12 February 2022 (1 day only)
                      Membership ID
                      : <corp pass number>

                      
                      For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system. 
                      Attached is the authorisation letter to <attraction name>. Please check that the details are accurate. 
                      Please take note of the following for your visit to <attraction name>:
                      Present this email confirmation to the General Office to collect the membership card(s).
                      Collect the membership card(s) from the General Office one day before your visit date and return the membership card(s) by 9am the next working day after your visit. 
                      Present the membership card(s), the authorisation letter and your staff pass at the entrance of <attraction name>. 
                      Entry is strictly based on the membership card(s) and the authorisation letter.
                      Your presence is required on the day of visit. Entry will be denied without staff’s presence. 
                      Your booking is non-transferable. 
                      Visit date is strictly based on the date stated in this email and the authorisation letter. 
                      Staff found abusing the membership(s) will be subject to disciplinary actions. 
                      Enjoy your visit to <attraction name>! 

                      Warm regards
                      HR Department `,
          }
      ]
    },
    methods: {
            saveContent(){
                console.log(this.EmailTemplates[0]["templateDraft"])
                // console.log(this.content);
                // this.content = this.content["ops"][0]["insert"]
                // console.log(this.content);

            },
        },
}

</script>

<style scoped>
</style>
