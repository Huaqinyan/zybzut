<template>
    <a-layout>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <p>
                <a-button type="primary" @click="add()">
                    新增
                </a-button>
            </p>
            <a-table
                :columns="columns"
                :row-key="record => record.id"
                :data-source="ebooks"
                :pagination="pagination"
                :loading="loading"
                @change="handleTableChange"
            >
                <template #cover="{ text: cover }">
                    <img v-if="cover" :src="cover" alt="avatar"/>
                </template>
                <template v-slot:action="{ text, record }">
                    <a-space size="small">
                        <a-button type="primary" @click="edit(record)">
                            编辑
                        </a-button>
                        <a-popconfirm
                                title="删除后不可恢复，确认删除"
                                ok-text="是"
                                cancel-text="否"
                                @confirm="HandleDelete(record.id)"
                        >
                            <a-button type="danger">
                                删除
                            </a-button>
                        </a-popconfirm>
                    </a-space>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>
    <a-modal v-model:visible="visible" :confirm-loading="modalLoading" title="电子书表单" @ok="handleOk">
        <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="封面">
                <a-input v-model:value="ebook.cover" />
            </a-form-item>
            <a-form-item label="名称">
                <a-input v-model:value="ebook.name" />
            </a-form-item>
            <a-form-item label="分类">
                <a-cascader
                        v-model:value="categoryIds"
                        :field-names="{ label: 'name', value: 'id', children: 'children' }"
                        :options="level1"
                />
            </a-form-item>
            <a-form-item label="描述">
                <a-input v-model:value="ebook.description" type="textarea" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script lang="ts">
    import {defineComponent, onMounted, ref} from 'vue';
    import axios from 'axios';
    import {message} from "ant-design-vue";

    export default defineComponent({
        name: 'AdminEbook',
        setup() {
            const ebooks = ref();
            const pagination = ref({
                current: 1,
                pageSize: 4,
                total: 0
            });

            const columns = [
                {
                    title: '封面',
                    dataIndex: 'cover',
                    slots: {customRender: 'cover'}
                },
                {
                    title: '名称',
                    dataIndex: 'name'
                },
                {
                    title: '分类',
                    slots: {customRender: 'category'}
                },
                {
                    title: '文档数',
                    dataIndex: 'docCount'
                },
                {
                    title: '阅读数',
                    dataIndex: 'viewCount'
                },
                {
                    title: '点赞数',
                    dataIndex: 'voteCount'
                },
                {
                    title: 'Action',
                    key: 'action',
                    slots: {customRender: 'action'}
                }
            ];

            /**
             * 数据查询
             **/
            const handleQuery = (p: any) => {
              loading.value=true;
              axios.get("/ebook/list",{
                  params:{
                      page:p.page,
                      size:p.size,
                  }
              }).then((response)=>{
                loading.value=false;
                const data=response.data;
                ebooks.value=data.content.list;
                //重置分页按钮
                pagination.value.current=p.page;
                pagination.value.total=data.content.total;
              })
            };

            /**
             * 表格点击页码时触发
             */
            const handleTableChange = (pagination: any) => {
                console.log("看看自带的分页参数都有啥：" + pagination);
                handleQuery({
                    page: pagination.current,
                    size: pagination.pageSize
                });
            };

            //表单
            const ebook=ref();
            const visible=ref(false);
            const loading = ref(false);
            const modalLoading = ref(false);
            const handleOk = (e: MouseEvent) => {
                console.log(e);
                modalLoading.value=true;
                axios.post("/ebook/save",ebook.value).then((response)=>{
                    modalLoading.value=false;
                    const data=response.data;
                    if (data.success){
                        visible.value=false;
                        //重新加载列表
                        handleQuery({
                            page:pagination.value.current,
                            size:pagination.value.pageSize,
                        })
                    }else{
                        message.error(data.message);
                    }
                })
            };

            const edit = (record:any) => {
                visible.value = true;
                ebook.value=record;
            };

            const add = () => {
                visible.value = true;
                ebook.value={};
            };

            const HandleDelete =(id: number) =>{
                console.log("id是"+id);
                axios.delete("/ebook/delete/"+id).then((response)=>{
                    const data=response.data;// data = commonResp
                    if (data.success){
                        //重新加载列表
                        handleQuery({
                            page:pagination.value.current,
                            size:pagination.value.pageSize,
                        });
                    }else{
                        message.error(data.message);
                    }
                });
            };


            onMounted(() => {
                handleQuery({
                    // 要与req中的名字一样才能映射到后端
                    page:1,
                    size:pagination.value.pageSize,
                });
            });


            return {
                ebook,
                ebooks,
                pagination,
                columns,
                loading,
                handleTableChange,
                visible,

                edit,
                add,
                HandleDelete,

                handleOk,
                handleQuery,
                modalLoading,
            }
        }
    });
</script>

<style scoped>
    img {
        width: 50px;
        height: 50px;
    }
</style>