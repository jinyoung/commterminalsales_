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
                    <v-select
                        v-model="selectedManufacturer"
                        :items="manufacturers"
                        label="제조사"
                        :rules="[rules.required]"
                        required
                    ></v-select>
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
                <v-select
                    v-model="searchParams.manufacturer"
                    :items="manufacturers"
                    label="제조사"
                ></v-select>
                <v-btn @click="getSpecDetail" color="primary">조회</v-btn>
                <v-list>
                    <v-list-item-group>
                        <template v-if="specDetails.length === 0">
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>조회된 스펙이 없습니다.</v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </template>
                        <v-list-item v-for="(spec, index) in specDetails" :key="index">
                            <v-list-item-content>
                                <v-list-item-title>{{ spec.spec }}</v-list-item-title>
                                <v-list-item-subtitle>{{ spec.phoneColor }} - {{ spec.phoneType }}</v-list-item-subtitle>
                                <Photo v-if="spec.image" v-model="spec.image" :editMode="false" />
                                <v-btn @click="addToComparison(spec)" color="success">비교 추가</v-btn>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-card-text>
        </v-card>
        <v-card>
            <v-card-title>
                비교 목록
            </v-card-title>
            <v-card-text>
                <v-data-table
                    :headers="comparisonHeaders"
                    :items="comparisonList"
                    class="elevation-1"
                ></v-data-table>
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
                selectedManufacturer: null,
                manufacturers: [],
                searchParams: {
                    color: '',
                    manufacturer: ''
                },
                specDetails: [],
                comparisonList: [], // List to hold selected specs for comparison
                comparisonHeaders: [
                    { text: 'Spec', value: 'spec' },
                    { text: 'Phone Color', value: 'phoneColor' },
                    { text: 'Phone Type', value: 'phoneType' },
                    { text: 'Image', value: 'image' }
                ],
                rules: {
                    required: value => !!value || '필수 항목입니다.'
                }
            };
        },
        async created() {
            this.manufacturers = await this.fetchManufacturers();
        },
        methods: {
            async fetchManufacturers() {
                return ['Samsung', 'Apple', 'LG', 'Nokia']; // Example manufacturers
            },
            async saveSpecComparation() {
                try {
                    const response = await axios.post('/specComparations', {
                        productId: this.specComparation.productId,
                        options: this.specComparation.options.split(','),
                        customerId: this.specComparation.customerId,
                        manufacturer: this.selectedManufacturer
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
                    this.specDetails = response.data._embedded.specs;
                    console.log('스펙 상세 조회 성공:', this.specDetails);
                } catch (error) {
                    console.error('스펙 상세 조회 실패:', error);
                }
            },
            addToComparison(spec) {
                // Add the selected spec to the comparison list
                this.comparisonList.push(spec);
                console.log('비교 목록에 추가됨:', spec);
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
