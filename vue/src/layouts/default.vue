<template>
  <v-app>
    <v-main>
      <router-view />
    </v-main>
    <AppFooter />
  </v-app>

  <v-dialog
    v-model="showRequestDialog"
    width="auto"
  >
    <v-card max-width="400" text="Your application will relaunch automatically after the update is complete.">
    </v-card>
  </v-dialog>
</template>

<script lang="ts" setup>
import {useAppStore} from "@/stores/app";

const showRequestDialog = useAppStore().showRequestDialog
</script>
