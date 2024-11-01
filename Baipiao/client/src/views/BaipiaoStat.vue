<template>
  <div>
    <hr />
    <!-- Map List -->
    <div class="row my-5">
      <div class="col-md-4">
        <PolarArea :data="eventcategories" :options="eventcategoriesOptions" />
      </div>
      <div class="col-md-4">
        <Bar :data="eventcategories" :options="eventcategoriesOptions" />
      </div>
      <div class="col-md-4">
        <Pie :data="eventOrganizers" :options="eventOragnizersOptions" />
      </div>
    </div>
    <div class="row chart-container">
      <div class="col-md-6">
        <LineChart :data="dailyEvents" :options="dailyEventsOptions" />
      </div>
      <div class="col-md-6">
        <LineChart :data="dailyEvents" :options="dailyEventsOptions" />
      </div>
    </div>
  </div>
</template>

<script>
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,TimeScale,
  RadialLinearScale, ArcElement

} from 'chart.js'
import { Bar, Line as LineChart, PolarArea, Pie } from 'vue-chartjs' 

ChartJS.register(CategoryScale, LinearScale, BarElement, TimeScale, Title, Tooltip, Legend, PointElement,RadialLinearScale, ArcElement, LineElement);

export default {
  components: {
    Bar,
    LineChart,
    PolarArea,
    Pie
  },
  data() {
    return {
      eventcategories: {
        labels: [],
        datasets: [{ data: [] }]
      },
      eventOrganizers: {
        labels: [],
        datasets: [{ data: [] }]
      },
      dailyEvents: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [
          {
            label: 'Data One',
            backgroundColor: '#f87979',
            data: [40, 39, 10, 40, 39, 80, 40]
          }
        ]
      },
      eventcategoriesOptions: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Event Categories'
          },
          legend: {
            display: false // Hide legend if you only have one dataset
          },
          tooltip: {
            callbacks: {
              label: (context) => `${context.label}: ${context.raw} events`
            }
          }
        },
        scales: {
          x: {
            title: {
              display: true,
              text: 'Categories'
            }
          },
          y: {
            title: {
              display: true,
              text: 'Event Count'
            },
            beginAtZero: true
          }
        }
      },

      dailyEventsOptions: {
        responsive: true,
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Daily Events'
          },
          legend: {
            display: false // Hide legend if you only have one dataset
          },
          tooltip: {
            callbacks: {
              label: (context) => `${context.label}: ${context.raw} events`
            }
          }
        },
        scales: {
          x: {
            title: {
              display: true,
              text: 'Day of the Month'
            }
          },
          y: {
            title: {
              display: true,
              text: 'Event Count'
            },
            beginAtZero: true
          }
        }
      },
      eventOragnizersOptions: {
        responsive: true,
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Event Organizers'
          },
          legend: {
            display: false // Hide legend if you only have one dataset
          },
          tooltip: {
            callbacks: {
              label: (context) => `${context.label}: ${context.raw} events`
            }
          }
        },
        
      }
    }
  },
  methods: {
    async fetchData() {
      try {
        const eventcategoriesResponse = await this.$http.get("eventcategories");
        const eventcategories = eventcategoriesResponse.data;

        // Extract labels and counts
        const labels = eventcategories.map(eventcategory => eventcategory.name);
        const counts = eventcategories.map(eventcategory => eventcategory.count);

        // Prepare the data in the specified format
        this.eventcategories = {
          labels: labels,
          datasets: [{
            data: counts,
            backgroundColor: ['#41B883', '#E46651','#00CCCC', '#FF00FF', '#36A2EB', '#FF6384',  '#FFCE56',  '#36A2EB'],
            // backgroundColor: 'rgba(75, 192, 192, 0.6)',
            // borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          }]
        };




        /////

        const dailyEventsResponse = await this.$http.get("dailyEvents");
        const dailyEvents = dailyEventsResponse.data;

        this.dailyEvents = {
          labels: dailyEvents.map(dailyEvent => dailyEvent.date),
          datasets: [{
            
            data: dailyEvents.map(dailyEvent => dailyEvent.count),
            backgroundColor: 'rgba( 192, 75, 192, 0.6)',
            borderColor: 'rgba(192, 75, 192, 1)',
            borderWidth: 1
          }]
        };


        /////

        const eventOrganizersResponse = await this.$http.get("eventorganizers");
        const eventOrganizers = eventOrganizersResponse.data;

        this.eventOrganizers = {
          labels: eventOrganizers.map(eventOrganizer => eventOrganizer.name),
          datasets: [{
            
            data: eventOrganizers.map(eventOrganizer => eventOrganizer.count),
            backgroundColor: ['#FF5733', '#33FF57', '#3357FF', '#FF33A2', '#FFAA33', '#33FFC5', '#C533FF', '#FF3333'],
            borderWidth: 1
          }]
        };

      } catch (error) {
        console.error("Error fetching events:", error);
      }
    },
  },
  created() {
    this.fetchData(); // Fetch events when the component is created
  },
};
</script>

<style scoped>
.chart-container {
  height: 400px; /* Set your desired height */
  width: 100%; /* Optional: set width */
}
</style>