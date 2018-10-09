/*
 * Copyright 2012 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

//$.namespace('azkaban');

// var handleJobMenuClick = function(action, el, pos) {
//   var jobid = el[0].jobid;
//   var requestURL = contextURL + "/manager?project=" + projectName + "&flow=" +
//       flowId + "&job=" + jobid;
//   if (action == "open") {
//     window.location.href = requestURL;
//   }
//   else if (action == "openwindow") {
//     window.open(requestURL);
//   }
// }

// var flowTabView;
// FlowTabView = Backbone.View.extend({
//   events: {
//     "click #graphViewLink": "handleGraphLinkClick",
//     "click #executionsViewLink": "handleExecutionLinkClick",
//     "click #summaryViewLink": "handleSummaryLinkClick"
//   },
//
//   initialize: function(settings) {
//     var selectedView = settings.selectedView;
//     if (selectedView == "executions") {
//       this.handleExecutionLinkClick();
//     }
//     else {
//       this.handleGraphLinkClick();
//     }
//   },
//
//   render: function() {
//     console.log("render graph");
//   },
//
//   handleGraphLinkClick: function(){
//     $("#executionsViewLink").removeClass("active");
//     $("#graphViewLink").addClass("active");
//     $('#summaryViewLink').removeClass('active');
//
//     $("#executionsView").hide();
//     $("#graphView").show();
//     $('#summaryView').hide();
//   },
//
//   handleExecutionLinkClick: function() {
//     $("#graphViewLink").removeClass("active");
//     $("#executionsViewLink").addClass("active");
//     $('#summaryViewLink').removeClass('active');
//
//     $("#graphView").hide();
//     $("#executionsView").show();
//     $('#summaryView').hide();
//     executionModel.trigger("change:view");
//   },
//
//   handleSummaryLinkClick: function() {
//     $('#graphViewLink').removeClass('active');
//     $('#executionsViewLink').removeClass('active');
//     $('#summaryViewLink').addClass('active');
//
//     $('#graphView').hide();
//     $('#executionsView').hide();
//     $('#summaryView').show();
//   },
// });
//
// var jobListView;
// var svgGraphView;
// var executionsView;
//
// ExecutionsView = Backbone.View.extend({
//   events: {
//     "click #pageSelection li": "handleChangePageSelection"
//   },
//
//   initialize: function(settings) {
//     this.model.bind('change:view', this.handleChangeView, this);
//     this.model.bind('render', this.render, this);
//     this.model.set({page: 1, pageSize: 16});
//     this.model.bind('change:page', this.handlePageChange, this);
//   },
//
//   render: function(evt) {
//     console.log("render");
//     // Render page selections
//     var tbody = $("#execTableBody");
//     tbody.empty();
//
//     var executions = this.model.get("executions");
//     for (var i = 0; i < executions.length; ++i) {
//       var row = document.createElement("tr");
//
//       var tdId = document.createElement("td");
//       var execA = document.createElement("a");
//       $(execA).attr("href", contextURL + "/executor?execid=" + executions[i].execId);
//       $(execA).text(executions[i].execId);
//       tdId.appendChild(execA);
//       row.appendChild(tdId);
//
//       var tdUser = document.createElement("td");
//       $(tdUser).text(executions[i].submitUser);
//       row.appendChild(tdUser);
//
//       var startTime = "-";
//       if (executions[i].startTime != -1) {
//         var startDateTime = new Date(executions[i].startTime);
//         startTime = getDateFormat(startDateTime);
//       }
//
//       var tdStartTime = document.createElement("td");
//       $(tdStartTime).text(startTime);
//       row.appendChild(tdStartTime);
//
//       var endTime = "-";
//       var lastTime = executions[i].endTime;
//       if (executions[i].endTime != -1) {
//         var endDateTime = new Date(executions[i].endTime);
//         endTime = getDateFormat(endDateTime);
//       }
//       else {
//         lastTime = (new Date()).getTime();
//       }
//
//       var tdEndTime = document.createElement("td");
//       $(tdEndTime).text(endTime);
//       row.appendChild(tdEndTime);
//
//       var tdElapsed = document.createElement("td");
//       $(tdElapsed).text( getDuration(executions[i].startTime, lastTime));
//       row.appendChild(tdElapsed);
//
//       var tdStatus = document.createElement("td");
//       var status = document.createElement("div");
//       $(status).addClass("status");
//       $(status).addClass(executions[i].status);
//       $(status).text(statusStringMap[executions[i].status]);
//       tdStatus.appendChild(status);
//       row.appendChild(tdStatus);
//
//       var tdAction = document.createElement("td");
//       row.appendChild(tdAction);
//
//       tbody.append(row);
//     }
//
//     this.renderPagination(evt);
//   },
//
//   renderPagination: function(evt) {
//     var total = this.model.get("total");
//     total = total? total : 1;
//     var pageSize = this.model.get("pageSize");
//     var numPages = Math.ceil(total / pageSize);
//
//     this.model.set({"numPages": numPages});
//     var page = this.model.get("page");
//
//     //Start it off
//     $("#pageSelection .active").removeClass("active");
//
//     // Disable if less than 5
//     console.log("Num pages " + numPages)
//     var i = 1;
//     for (; i <= numPages && i <= 5; ++i) {
//       $("#page" + i).removeClass("disabled");
//     }
//     for (; i <= 5; ++i) {
//       $("#page" + i).addClass("disabled");
//     }
//
//     // Disable prev/next if necessary.
//     if (page > 1) {
//       $("#previous").removeClass("disabled");
//       $("#previous")[0].page = page - 1;
//       $("#previous a").attr("href", "#page" + (page - 1));
//     }
//     else {
//       $("#previous").addClass("disabled");
//     }
//
//     if (page < numPages) {
//       $("#next")[0].page = page + 1;
//       $("#next").removeClass("disabled");
//       $("#next a").attr("href", "#page" + (page + 1));
//     }
//     else {
//       $("#next")[0].page = page + 1;
//       $("#next").addClass("disabled");
//     }
//
//     // Selection is always in middle unless at barrier.
//     var startPage = 0;
//     var selectionPosition = 0;
//     if (page < 3) {
//       selectionPosition = page;
//       startPage = 1;
//     }
//     else if (page == numPages) {
//       selectionPosition = 5;
//       startPage = numPages - 4;
//     }
//     else if (page == numPages - 1) {
//       selectionPosition = 4;
//       startPage = numPages - 4;
//     }
//     else {
//       selectionPosition = 3;
//       startPage = page - 2;
//     }
//
//     $("#page"+selectionPosition).addClass("active");
//     $("#page"+selectionPosition)[0].page = page;
//     var selecta = $("#page" + selectionPosition + " a");
//     selecta.text(page);
//     selecta.attr("href", "#page" + page);
//
//     for (var j = 0; j < 5; ++j) {
//       var realPage = startPage + j;
//       var elementId = "#page" + (j+1);
//
//       $(elementId)[0].page = realPage;
//       var a = $(elementId + " a");
//       a.text(realPage);
//       a.attr("href", "#page" + realPage);
//     }
//   },
//
//   handleChangePageSelection: function(evt) {
//     if ($(evt.currentTarget).hasClass("disabled")) {
//       return;
//     }
//     var page = evt.currentTarget.page;
//     this.model.set({"page": page});
//   },
//
//   handleChangeView: function(evt) {
//     if (this.init) {
//       return;
//     }
//     console.log("init");
//     this.handlePageChange(evt);
//     this.init = true;
//   },
//
//   handlePageChange: function(evt) {
//     var page = this.model.get("page") - 1;
//     var pageSize = this.model.get("pageSize");
//     var requestURL = contextURL + "/manager";
//
//     var model = this.model;
//     var requestData = {
//       "project": projectName,
//       "flow": flowId,
//       "ajax": "fetchFlowExecutions",
//       "start": page * pageSize,
//       "length": pageSize
//     };
//     var successHandler = function(data) {
//       model.set({
//         "executions": data.executions,
//         "total": data.total
//       });
//       model.trigger("render");
//     };
//     $.get(requestURL, requestData, successHandler, "json");
//   }
// });
//
// var summaryView;
// SummaryView = Backbone.View.extend({
//   events: {
//     'click #analyze-btn': 'fetchLastRun'
//   },
//
//   initialize: function(settings) {
//     this.model.bind('change:view', this.handleChangeView, this);
//     this.model.bind('render', this.render, this);
//
//     this.fetchDetails();
//     this.fetchSchedule();
//     this.model.trigger('render');
//   },
//
//   fetchDetails: function() {
//     var requestURL = contextURL + "/manager";
//     var requestData = {
//       'ajax': 'fetchflowdetails',
//       'project': projectName,
//       'flow': flowId
//     };
//
//     var model = this.model;
//
//     var successHandler = function(data) {
//       console.log(data);
//       model.set({
//         'jobTypes': data.jobTypes
//       });
//       model.trigger('render');
//     };
//     $.get(requestURL, requestData, successHandler, 'json');
//   },
//
//   fetchSchedule: function() {
//     var requestURL = contextURL + "/schedule"
//     var requestData = {
//       'ajax': 'fetchSchedule',
//       'projectId': projectId,
//       'flowId': flowId
//     };
//     var model = this.model;
//     var view = this;
//     var successHandler = function(data) {
//       model.set({'schedule': data.schedule});
//       model.trigger('render');
//       view.fetchSla();
//     };
//     $.get(requestURL, requestData, successHandler, 'json');
//   },
//
//   fetchSla: function() {
//     var schedule = this.model.get('schedule');
//     if (schedule == null || schedule.scheduleId == null) {
//       return;
//     }
//
//     var requestURL = contextURL + "/schedule"
//     var requestData = {
//       "scheduleId": schedule.scheduleId,
//       "ajax": "slaInfo"
//     };
//     var model = this.model;
//     var successHandler = function(data) {
//       if (data == null || data.settings == null || data.settings.length == 0) {
//         return;
//       }
//       schedule.slaOptions = true;
//       model.set({'schedule': schedule});
//       model.trigger('render');
//     };
//     $.get(requestURL, requestData, successHandler, 'json');
//   },
//
//   fetchLastRun: function() {
//     var requestURL = contextURL + "/manager";
//     var requestData = {
//       'ajax': 'fetchLastSuccessfulFlowExecution',
//       'project': projectName,
//       'flow': flowId
//     };
//     var view = this;
//     var successHandler = function(data) {
//       if (data.success == "false" || data.execId == null) {
//         dust.render("flowstats-no-data", data, function(err, out) {
//           $('#flow-stats-container').html(out);
//         });
//         return;
//       }
//       flowStatsView.show(data.execId);
//     };
//     $.get(requestURL, requestData, successHandler, 'json');
//   },
//
//   handleChangeView: function(evt) {
//   },
//
//   render: function(evt) {
//     var data = {
//       projectName: projectName,
//       flowName: flowId,
//           jobTypes: this.model.get('jobTypes'),
//       schedule: this.model.get('schedule'),
//     };
//     dust.render("flowsummary", data, function(err, out) {
//       $('#summary-view-content').html(out);
//     });
//   },
// });


//
// var executionModel;
// ExecutionModel = Backbone.Model.extend({});
//
// var summaryModel;
// SummaryModel = Backbone.Model.extend({});
//
// var flowStatsView;
// var flowStatsModel;
//
// var executionsTimeGraphView;
// var slaView;

//var id = $("#myDefine").val();
// id="531393519067848704";
//$.post(ctx + 'lineage/all', { "tableId": id }, function(data) {



//});
$(function() {
    var graphModel;
    var mainSvgGraphView;
    graphModel = new GraphModel();
    console.log("data fetched");
    data= {"nodes":[{"in":["ods_table_1"],"id":"blg_order_01.mysql_table_1","type":"command"},{"in":["ods_table_1"],"id":"blg_order_01.mysql_table_2","type":"command"},{"in":["ods_table_4"],"id":"blg_order_01.mysql_table_3","type":"command"},{"in":["ods_table_4"],"id":"blg_order_01.mysql_table_4","type":"command"},{"in":["ods_table_2"],"id":"blg_order_01.mysql_table_5","type":"command"},{"in":["ods_table_2"],"id":"blg_order_01.mysql_table_6","type":"command"},{"in":["ods_table_5"],"id":"blg_order_01.mysql_table_7","type":"command"},{"in":["ods_table_5"],"id":"blg_order_01.mysql_table_8","type":"command"},{"in":["ods_table_3"],"id":"blg_order_01.mysql_table_9","type":"command"},{"in":["ods_table_3"],"id":"blg_order_01.mysql_table_10","type":"command"},{"in":["ods_table_3"],"id":"blg_order_01.mysql_table_11","type":"command"},{"in":["dw_table_1"],"id":"ods_table_1","type":"command"},{"in":["dw_table_3"],"id":"ods_table_4","type":"command"},{"in":["dw_table_4"],"id":"ods_table_5","type":"command"},{"in":["dw_table_2"],"id":"ods_table_2","type":"command"},{"in":["dw_table_2"],"id":"ods_table_3","type":"command"},{"in":["dm_table_1"],"id":"dw_table_1","type":"command"},{"in":["dm_table_1"],"id":"dw_table_2","type":"command"},{"in":["dm_table_1"],"id":"dw_table_3","type":"command"},{"in":["dm_table_1"],"id":"dw_table_4","type":"command"},{"id":"指标1","type":"command"},{"in":["指标1"],"id":"dm_table_1","type":"command"},{"in":["blg_order_01.mysql_table_1","blg_order_01.mysql_table_2","blg_order_01.mysql_table_3","blg_order_01.mysql_table_4","blg_order_01.mysql_table_5","blg_order_01.mysql_table_6","blg_order_01.mysql_table_7","blg_order_01.mysql_table_8","blg_order_01.mysql_table_9","blg_order_01.mysql_table_10","blg_order_01.mysql_table_11"],"id":"end","type":"command"}],"project":"dws_dm_user_driver_order_workflow","projectId":93,"flow":"end"};
    //data={"nodes":[{"in":["dws_pty_yc_driver_his_day_2"],"id":"dm_active_driver_day","type":"command"},{"in":["dws_pty_yc_user_his_day_3"],"id":"dm_active_user_detail_day","type":"command"},{"in":["dws_pty_yc_corporate_his_day_4"],"id":"dm_corporate_balance_day","type":"command"},{"in":["dws_pty_yc_user_his_day_3"],"id":"dm_coupon_info_day","type":"command"},{"in":["dws_pty_yc_driver_his_day_2"],"id":"dm_driver_analysis_day","type":"command"},{"in":["edw_run"],"id":"dm_operation_dispatch_total_day","type":"command"},{"in":["dws_agt_yc_order_his_day_1"],"id":"dm_operation_driver_service_day","type":"command"},{"in":["dws_pty_yc_user_his_day_3"],"id":"dm_operation_order_base_day","type":"command"},{"in":["dws_pty_yc_corporate_his_day_4","dws_pty_yc_user_his_day_3"],"id":"dm_operation_user_stat_day","type":"command"},{"in":["dws_pty_yc_user_his_day_3"],"id":"dm_recharge_user_action_day","type":"command"},{"in":["dws_pty_yc_user_his_day_3"],"id":"dm_user_analysis_day","type":"command"},{"in":["edw_run"],"id":"dws_agt_yc_order_his_day_1","type":"command"},{"in":["dws_agt_yc_order_his_day_1"],"id":"dws_pty_yc_corporate_his_day_4","type":"command"},{"in":["dws_agt_yc_order_his_day_1"],"id":"dws_pty_yc_driver_his_day_2","type":"command"},{"in":["dws_agt_yc_order_his_day_1"],"id":"dws_pty_yc_user_his_day_3","type":"command"},{"id":"edw_run","type":"command"},{"in":["dm_active_driver_day","dm_active_user_detail_day","dm_corporate_balance_day","dm_coupon_info_day","dm_driver_analysis_day","dm_operation_dispatch_total_day","dm_operation_driver_service_day","dm_operation_order_base_day","dm_operation_user_stat_day","dm_recharge_user_action_day","dm_user_analysis_day"],"id":"end","type":"command"}],"project":"dws_dm_user_driver_order_workflow","projectId":93,"flow":"end"};
    graphModel.addFlow(data);

    mainSvgGraphView = new SvgGraphView({
        el: $('#svgDiv'),
        model: graphModel,
        rightClick: {
           "node": nodeClickCallback
        //   "edge": edgeClickCallback,
        //   "graph": graphClickCallback
         }
    });
  // var selected;
  // // Execution model has to be created before the window switches the tabs.
  // // executionModel = new ExecutionModel();
  // // executionsView = new ExecutionsView({
  //   el: $('#executionsView'),
  //   model: executionModel
  // });
  //
  // summaryModel = new SummaryModel();
  // summaryView = new SummaryView({
  //   el: $('#summaryView'),
  //   model: summaryModel
  // });
  //
  // flowStatsModel = new FlowStatsModel();
  // flowStatsView = new FlowStatsView({
  //   el: $('#flow-stats-container'),
  //   model: flowStatsModel
  // });
  //
  // flowTabView = new FlowTabView({
  //   el: $('#headertabs'),
  //   selectedView: selected
  // });



  // $(document).ready(function () {



  // })


  // graphModel = new GraphModel();
  // var data =
  //     {"nodes":[{"in":["msck_bit_partition_table1","msck_bit_partition_table2","msck_bit_partition_table3","msck_etl_partition_table1","msck_etl_partition_table2","msck_ods_partition_table1","msck_ods_partition_table2","msck_ods_partition_table3","msck_ods_partition_table4","msck_ods_partition_table5","msck_ods_partition_table6","msck_ods_partition_table7","msck_other_partition_table","msck_test_partiton_table"],"id":"end","type":"command"},{"id":"msck_bit_partition_table1","type":"command"},{"id":"msck_bit_partition_table2","type":"command"},{"id":"msck_bit_partition_table3","type":"command"},{"id":"msck_etl_partition_table1","type":"command"},{"id":"msck_etl_partition_table2","type":"command"},{"id":"msck_ods_partition_table1","type":"command"},{"id":"msck_ods_partition_table2","type":"command"},{"id":"msck_ods_partition_table3","type":"command"},{"id":"msck_ods_partition_table4","type":"command"},{"id":"msck_ods_partition_table5","type":"command"},{"id":"msck_ods_partition_table6","type":"command"},{"id":"msck_ods_partition_table7","type":"command"},{"id":"msck_other_partition_table","type":"command"},{"id":"msck_test_partiton_table","type":"command"}],"project":"msck_all_partition_table","projectId":150,"flow":"end"};
  // console.log("data fetched");
  // graphModel.addFlow(data);
  // graphModel.trigger("change:graph");
  //
  // mainSvgGraphView = new SvgGraphView({
  //   el: $('#svgDiv'),
  //   model: graphModel
  //   // rightClick: {
  //   //   "node": nodeClickCallback,
  //   //   "edge": edgeClickCallback,
  //   //   "graph": graphClickCallback
  //   // }
  // });

  // jobsListView = new JobListView({
  //   el: $('#joblist-panel'),
  //   model: graphModel,
  //   contextMenuCallback: jobClickCallback
  // });
  //
  // executionsTimeGraphView = new TimeGraphView({
  //   el: $('#timeGraph'),
  //   model: executionModel,
  //   modelField: 'executions'
  // });

  // slaView = new ChangeSlaView({el:$('#sla-options')});

  // var requestURL = contextURL + "/manager";
  // Set up the Flow options view. Create a new one every time :p
  // $('#executebtn').click(function() {
  //   var data = graphModel.get("data");
  //   var nodes = data.nodes;
  //   var executingData = {
  //     project: projectName,
  //     ajax: "executeFlow",
  //     flow: flowId
  //   };
  //
  //   flowExecuteDialogView.show(executingData);
  // });

  // var requestData = {
  //   "project": projectName,
  //   "ajax": "fetchflowgraph",
  //   "flow": flowId
  // };

    // var successHandler = function(data) {
    // console.log("data fetched");
    // graphModel.addFlow(data);
    // graphModel.trigger("change:graph");

  //   // Handle the hash changes here so the graph finishes rendering first.
  //   if (window.location.hash) {
  //     var hash = window.location.hash;
  //     if (hash == "#executions") {
  //       flowTabView.handleExecutionLinkClick();
  //     }
  //     if (hash == "#summary") {
  //       flowTabView.handleSummaryLinkClick();
  //     }
  //     else if (hash == "#graph") {
  //       // Redundant, but we may want to change the default.
  //       selected = "graph";
  //     }
  //     else {
  //       if ("#page" == hash.substring(0, "#page".length)) {
  //         var page = hash.substring("#page".length, hash.length);
  //         console.log("page " + page);
  //         flowTabView.handleExecutionLinkClick();
  //         executionModel.set({"page": parseInt(page)});
  //       }
  //       else {
  //         selected = "graph";
  //       }
  //     }
  //   }
  // };
  // $.get(requestURL, requestData, successHandler, "json");
});
