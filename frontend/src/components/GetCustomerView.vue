<template>

    <v-data-table
        :headers="headers"
        :items="getCustomer"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'GetCustomerView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            getCustomer : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/getCustomers'))

            temp.data._embedded.getCustomers.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.getCustomer = temp.data._embedded.getCustomers;
        },
        methods: {
        }
    }
</script>

