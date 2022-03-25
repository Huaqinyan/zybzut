<template>
    <a-layout>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <p>
                <a-form
                        layout="inline"
                        :model="param"
                >
                    <a-form-item>
                        <a-input v-model:value="param.name" placeholder="请输入关键字...">
                            <template #prefix><SearchOutlined style="color: rgba(0, 0, 0, 0.25)"/></template>
                        </a-input>
                    </a-form-item>
                    <a-form-item>
                        <a-button
                                type="primary"
                                @click="handleQuery({page:1,size:pagination.pageSize})"
                        >
                            搜索
                        </a-button>
                    </a-form-item>
                    <a-form-item>
                        <a-button type="primary" @click="add()">
                            新增
                        </a-button>
                    </a-form-item>
                </a-form>
            </p>

            <a-table
                :columns="columns"
                :row-key="record => record.id"
                :data-source="categorys"
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
    <a-modal v-model:visible="visible" :confirm-loading="modalLoading" title="分类表单" @ok="handleOk">
        <a-form :model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="名称">
                <a-input v-model:value="category.name" />
            </a-form-item>
            <a-form-item label="父分类">
                <a-input v-model:value="category.parent" />
            </a-form-item>
            <a-form-item label="顺序">
                <a-input v-model:value="category.sort" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script lang="ts">
    import {defineComponent, onMounted, ref} from 'vue';
    import axios from 'axios';
    import {message} from "ant-design-vue";
    import { Tool } from '@/util/tool';

    export default defineComponent({
        name: 'AdminCategory',
        setup() {
            const param=ref();
            param.value={};
            const categorys = ref();
            const pagination = ref({
                current: 1,
                pageSize: 5,
                total: 0
            });

            const columns = [
                {
                    title: '名称',
                    dataIndex: 'name'
                },
                {
                    title: '父分类',
                    dataIndex: 'parent'
                },
                {
                    title: '顺序',
                    dataIndex: 'sort'
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
              axios.get("/category/list",{
                  params:{
                      page:p.page,
                      size:p.size,
                      name:param.value.name,
                  }
              }).then((response)=>{
                loading.value=false;
                const data=response.data;
                if(data.success){
                    categorys.value=data.content.list;
                    //重置分页按钮
                    pagination.value.current=p.page;
                    pagination.value.total=data.content.total;
                }else{
                    message.error(data.message);
                }

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
            const category=ref();
            const visible=ref(false);
            const loading = ref(false);
            const handleOk = (e: MouseEvent) => {
                console.log(e);
                axios.post("/category/save",category.value).then((response)=>{
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
                category.value=Tool.copy(record);
            };

            const add = () => {
                visible.value = true;
                category.value={};
            };

            const HandleDelete =(id: number) =>{
                console.log("id是"+id);
                axios.delete("/category/delete/"+id).then((response)=>{
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
                category,
                categorys,
                pagination,
                columns,
                loading,
                handleTableChange,
                visible,
                param,

                edit,
                add,
                HandleDelete,

                handleOk,
                handleQuery,
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