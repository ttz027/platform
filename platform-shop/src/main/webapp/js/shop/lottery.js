$(function () {
    $("#jqGrid").Grid({
        url: '../lottery/list',
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '活动名称', name: 'name', index: 'name', width: 120},
            {label: '中奖名额', name: 'placesNum', index: 'places_num', width: 80},
            {
                label: '奖品类型', name: 'prizeType', index: 'prize_type', width: 80, formatter: function (value) {
                    if (value == 1) {
                        return '商品';
                    }
                }
            },
            {label: '商品ID', name: 'prizeId', index: 'prize_id', width: 80},
            {label: '商品名称', name: 'prizeName', index: 'prize_name', width: 80},
            {
                label: '上线时间',
                name: 'onlineTime',
                index: 'online_time',
                width: 120,
                formatter: function (value) {
                    return transDate(value);
                }
            },
            {
                label: '下线时间', name: 'downlineTime', index: 'downline_time', width: 120, formatter: function (value) {
                    return transDate(value);
                }
            },
            {
                label: '开奖时间',
                name: 'openTime',
                index: 'open_time',
                width: 120,
                formatter: function (value) {
                    return transDate(value);
                }
            },
            {
                label: '创建时间', name: 'createTime', index: 'create_time', width: 120, formatter: function (value) {
                    return transDate(value);
                }
            },
            {label: '活动状态', name: 'lotteryStatus', index: 'lottery_status', width: 80, formatter: function (value) {
                    if (value == 0) {
                        return '未开奖';
                    }else{
                        return '已开奖';
                    }
                }
            }
           ]
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showCard: false,
        showGoods: false,
        title: null,
        lottery: {},
        ruleValidate: {
            name: [
                {required: true, message: '活动名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            name: ''
        },
        goods: [],
        goodss: [],
        user: [],
        users: [],
        selectData: {},
        sendSms: ''//是否发送短信
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.showCard = true;
            vm.showGoods = false;
            vm.title = "新增";
            vm.lottery = {prizeType:1,placesNum:0};
            this.getGoodss();
        },
        update: function (event) {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.showCard = true;
            vm.showGoods = false;
            vm.title = "修改";
            this.getGoodss();
            vm.getInfo(id);
        },
        saveOrUpdate: function (event) {
            var url = vm.lottery.id == null ? "../lottery/save" : "../lottery/update";
            alert(url);
            Ajax.request({
                type: "POST",
                url: url,
                contentType: "application/json",
                params: JSON.stringify(vm.lottery),
                successCallback: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows("#jqGrid");
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    type: "POST",
                    url: "../lottery/delete",
                    contentType: "application/json",
                    params: JSON.stringify(ids),
                    successCallback: function (r) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        },
        getInfo: function (id) {
            Ajax.request({
                url: "../lottery/info/" + id,
                async: true,
                successCallback: function (r) {
                    vm.lottery = r.ad;
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.showCard = false;
            vm.showGoods = false;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },
        publish: function (id, sendType) {
            vm.showGoods = true;
            vm.goods = [];
            vm.user = [];
            vm.getGoodss();
            vm.getUsers();
            vm.selectData = {id: id, sendType: sendType};
            vm.sendSms = false;
            openWindow({
                title: "发放",
                area: ['600px', '350px'],
                content: jQuery("#sendDiv")
            })
        },
        getUsers: function () {
            Ajax.request({
                url: "../user/queryAll",
                async: true,
                successCallback: function (r) {
                    vm.users = r.list;
                }
            });
        },
        publishSubmit: function () {

            var sendType = vm.selectData.sendType;
            if (sendType == 1 && vm.user.length == 0) {
                vm.$Message.error('请选择下发会员');
                return;
            }
            if (sendType == 3 && vm.goods.length == 0) {
                vm.$Message.error('请选择下发商品');
                return;
            }
            confirm('确定下发优惠券？', function () {
                Ajax.request({
                    type: "POST",
                    dataType: 'json',
                    url: "../lottery/publish",
                    contentType: "application/json",
                    params: JSON.stringify({
                        sendType: vm.selectData.sendType,
                        lotteryId: vm.selectData.id,
                        goodsIds: vm.goods.toString(),
                        userIds: vm.user.toString(),
                        sendSms: vm.sendSms
                    }),
                    successCallback: function (r) {
                        alert('操作成功', function (index) {
                            vm.reload();
                            vm.showGoods = false;
                            vm.showList = true;
                        });
                    }
                });
            });
        },
        getGoodss: function () {
            Ajax.request({
                url: "../goods/queryAll/",
                async: true,
                successCallback: function (r) {
                    vm.goodss = r.list;
                }
            });
        },
        changeDate: function() {
            if (this.formatDates) {

                this.$refs["changeDatePicker"].onSelectionModeChange('time');//弹出时间选择框

                this.detailData.validTime = this.$refs.changeDatePicker.visualValue  //将当前组件的选择的年月日时分秒时间，赋值给当前组件

            }
        },
        getPrizeName: function(obj){
            vm.lottery.prizeId = vm.lottery.prize.id;
            vm.lottery.prizeName = vm.lottery.prize.name;
        }
    }
});