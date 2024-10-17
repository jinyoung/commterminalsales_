<template>
     <v-container>
        <v-card>
            <v-card-title>
                핸드폰 스펙 비교
            </v-card-title>
            <v-card-text>
                <v-form ref="form" v-model="valid">
                    <v-text-field
                        v-model="specComparation.productId"
                        label="Product ID"
                        :rules="[rules.required]"
                        required
                    ></v-text-field>
                    <v-textarea
                        v-model="specComparation.options"
                        label="Options (comma separated)"
                        :rules="[rules.required]"
                        required
                    ></v-textarea>
                    <v-text-field
                        v-model="specComparation.customerId"
                        label="Customer ID"
                        :rules="[rules.required]"
                        required
                    ></v-text-field>
                    <v-btn @click="saveSpecComparation" color="primary">스펙비교저장</v-btn>
                </v-form>
            </v-card-text>
        </v-card>
        <v-card>
            <v-card-title>
                스펙 상세 조회
            </v-card-title>
            <v-card-text>
                <v-text-field
                    v-model="searchParams.color"
                    label="색상"
                ></v-text-field>
                <v-text-field
                    v-model="searchParams.manufacturer"
                    label="제조사"
                ></v-text-field>
                <v-btn @click="getSpecDetail" color="primary">조회</v-btn>
                <v-list>
                    <v-list-item-group>
                        <v-list-item v-for="(spec, index) in specDetails" :key="index">
                            <v-list-item-content>
                                <v-list-item-title>{{ spec.spec }}</v-list-item-title>
                                <v-list-item-subtitle>{{ spec.phoneColor }} - {{ spec.phoneType }}</v-list-item-subtitle>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script>

    const axios = require('axios').default;
    
    export default {
        name: 'SpecSpecComparationManager',
        

        data() {
            return {
                valid: false,
                specComparation: {
                    productId: '',
                    options: '',
                    customerId: ''
                },
                searchParams: {
                    color: '',
                    manufacturer: ''
                },
                specDetails: [],
                rules: {
                    required: value => !!value || '필수 항목입니다.'
                }
            };
        },
        methods:{
            async saveSpecComparation() {
                try {
                    const response = await axios.post('/specComparations', {
                        productId: this.specComparation.productId,
                        options: this.specComparation.options.split(','),
                        customerId: this.specComparation.customerId
                    });
                    console.log('스펙 비교 저장 성공:', response.data);
                } catch (error) {
                    console.error('스펙 비교 저장 실패:', error);
                }
            },
            async getSpecDetail() {
                try {
                    const response = await axios.get(`/specs/search/FindByColorsAndManufacturer`, {
                        params: {
                            color: this.searchParams.color,
                            manufacturer: this.searchParams.manufacturer
                        }
                    });
                    this.specDetails = response.data;
                    console.log('스펙 상세 조회 성공:', this.specDetails);
                } catch (error) {
                    console.error('스펙 상세 조회 실패:', error);
                }
            }
        }
    };
</script>


<style>
    .video-card {
        width:300px; 
        margin-left:4.5%; 
        margin-top:50px; 
        margin-bottom:50px;
    }
</style>

