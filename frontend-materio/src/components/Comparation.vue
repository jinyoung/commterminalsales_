<template>

    <div>
        <div class="detail-title">
        Comparation
        </div>
        <v-col>
            <String label="ProductId" v-model="value.productId" :editMode="editMode"/>
            <String label="Options" v-model="value.options" :editMode="editMode"/>
            <String label="Manufacturer" v-model="value.manufacturer" :editMode="editMode"/>
        </v-col>

        <v-card-actions v-if="inList">
            <slot name="actions"></slot>
        </v-card-actions>
    </div>
</template>

<script>
import BaseEntity from './base-ui/BaseEntity.vue'
import BasePicker from './base-ui/BasePicker.vue'

export default {
    name: 'Comparation',
    mixins:[BaseEntity],
    components:{
        BasePicker
    },
    data: () => ({
        path: 'Comparations',
    }),
    props: {
    },
    watch: {
        value(val){
            this.value = val;
            this.change();
        },
    },
    async created(){
        this.value = this.modelValue
        if (this.value && this.value.id !== undefined) {
            this.value = await this.repository.findById(this.value.id)
        }
    },
    methods: {
        pick(val){
            this.value = val;
            this.change();
        },
    }
}
</script>

