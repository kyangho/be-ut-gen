import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'

export default function Home() {
  return (
    <form action="http://localhost:3000/api/java-parser" method='POST' encType='multipart-form'>
      <input type='file' name='file'></input>
      <input type='submit'></input>
    </form>
  )
}
