<template>
    <div>
        <textarea class="textarea" v-model="dataText" placeholder="Your JSON"></textarea>
        <br>
        <button class="button" @click="generate">Generate</button>
        <br>
        <h3 style="color: red" v-model="error">{{ error }}</h3>
        <hr class="hr-shelf">
        <el-card class="form">
            <FormSchema ref="formSchema" v-model="model" @submit.prevent
                        :schema="schema">
            </FormSchema>
            <button class="el-button" @click="clear">Clear</button>
        </el-card>
    </div>
</template>

<script>
import FormSchema from "@formschema/native";
import axios from "axios";

export default {
    data: () => ({
        schema: '',
        dataText: '',
        error: '',
        model: {}
    }),
    name: "InputJson",
    methods: {
        generate: function () {
            let jsonData = ''

            try {
                jsonData = JSON.parse(this.dataText)
                this.error = ''
            } catch (e) {
                this.error = 'Invalid JSON'
                return
            }
            axios.post("http://localhost:8080/api/generate", jsonData)
                .then(response => {
                    this.schema = response.data
                })
                .catch(e => {
                    this.error = e.message
                })
        },
        clear: function () {
            this.schema = ''
        }
    },
    components: {FormSchema}
}
</script>

<style scoped>
.hr-shelf {
    margin: -30px auto 25px;
    padding: 0;
    height: 50px;
    border: none;
    border-bottom: 1px solid #1f1209;
    box-shadow: 0 20px 20px -20px #333;
    width: 95%;
}

.el-button {
    background-color: #0b03fc;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    border-radius: 5px;
    margin-top: 10px;
    width: 100px;
    transition: all 0.5s ease;
}

.el-button:hover {
    width: 108px;
}

.button {
    background-color: #0b03fc;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    border-radius: 5px;
    margin-top: 10px;
    width: 200px;
    transition: all 0.5s ease;
}

.button:hover {
    width: 215px;
}

textarea::placeholder {
    font-size: 30px;
    font-style: normal;
    text-align: center;
}

.textarea {
    border-radius: 5px;
    width: 600px;
    text-align: left;
    height: 200px;
    font-size: 30px;
    resize: vertical;
    min-height: 100px;
    background: #f2faf9;
}

.textarea:focus {
    box-shadow: 0 0 0 0.2rem #a6ecff;
}
</style>

<style>
body {
    background: #defcf9;
}

.form {
    background: #f2faf9;
    text-align: left;
    width: 600px;
    margin: auto;
    font-size: 25px;
}

input {
    font-size: 20px;
    margin-bottom: 10px;
}


input:focus {
    box-shadow: 0 0 0 0.2rem #a6ecff;
}

h1 {
    font-size: 1.7em;
    text-align: center;
    margin-top: 0;
    margin-bottom: .2em
}

h1 + p {
    display: block;
    text-align: center;
    margin-bottom: 1.2em
}

small {
    line-height: 20px;
    display: block;
}

.el-button {
    background-color: #0b03fc;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    border-radius: 5px;
    margin-top: 10px;
}

input[type=checkbox] {
    width: 25px;
    height: 25px;
}
</style>
