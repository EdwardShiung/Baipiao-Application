<template>
    <div>
      <!--Stats cards-->
      <div class="row">
        <div
          class="col-md-6 col-xl-3"
          v-for="stats in statsCards"
          :key="stats.title"
        >
          <stats-card>
            <div
              class="icon-big text-center"
              :class="`icon-${stats.type}`"
              slot="header"
            >
              <i :class="stats.icon"></i>
            </div>
            <div class="numbers" slot="content">
              <p>{{ stats.title }}</p>
              {{ stats.value }}
            </div>
            <div class="stats" slot="footer">
              <i :class="stats.footerIcon"></i> {{ stats.footerText }}
            </div>
          </stats-card>
        </div>
      </div>
      <hr>
      <!-- Table List -->
        <div class="row my-5">
                    <div class="col">
                        <table class="table bg-white rounded shadow-lg  table-hover">
                            <thead>
                                <tr>
                                    <th scope="col" width="10">#</th>
                                    <th scope="col">Event</th>
                                    <th scope="col">Location</th>
                                    <th scope="col">Date</th>
                                    <th scope="col">Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">1</th>
                                    <td>Student Branch Chapter at Virginia</td>
                                    <td>Torgerson</td>
                                    <td>2023/03/01</td>
                                    <td>Finished</td>
                                </tr>
                                <tr>
                                    <th scope="row">2</th>
                                    <td>Amazon Job Event</td>
                                    <td>Newmen Library</td>
                                    <td>2023/03/02</td>
                                    <td>Finished</td>
                                </tr>
                                <tr>
                                    <th scope="row">3</th>
                                    <td>Bloomberg Job Event</td>
                                    <td>Torgerson</td>
                                    <td>2023/03/03</td>
                                    <td>Finished</td>
                                </tr>
                            
                            </tbody>
                        </table>
                    </div>
        </div>
    </div>
  </template>
  <script>
  import { StatsCard} from "@/components/index";

  export default {
    components: {
      StatsCard,
    },
    /**
     * Chart data used to render stats, charts. Should be replaced with server data
     */
    data() {
      return {
        statsCards: [
          {
            type: "warning",
            icon: "ti-pencil",
            title: "Today",
            value: "10",
            footerText: "Today",
            footerIcon: "ti-reload",
          },
          {
            type: "success",
            icon: "ti-pencil-alt",
            title: "Last Day",
            value: "4",
            footerText: "Last day",
            footerIcon: "ti-timer",
          },
          {
            type: "danger",
            icon: "ti-folder",
            title: "A Week Ago",
            value: "23",
            footerText: "A Week Ago",
            footerIcon: "ti-timer",
          },
          {
            type: "info",
            icon: "ti-server",
            title: "Month Ago",
            value: "+45",
            footerText: "Month Ago",
            footerIcon: "ti-calendar",
          },
        ],
      };
    },
    methods:{
        async fetchData(){

        }
    },
    created(){
    },
  };
  </script>
  <style></style>
