<template>
    <div>
        <String label="CustomerId" v-model="value.customerId" :editMode="editMode"/>
        <ComparationDetailGrid label="Comparations" offline v-model="value.comparations" :editMode="editMode" @change="change"/>

        <v-divider class="border-opacity-100 my-divider"></v-divider>
        <v-layout row justify-end>
            <v-btn
                width="64px"
                color="primary"
                @click="save"
            >
                저장
            </v-btn>
        </v-layout>
    </div>
</template>


<script>

import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'SpecComparation',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "specComparations"
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
