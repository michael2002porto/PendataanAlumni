package pnj.uts.ti.michael_natanael.ui.news

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import pnj.uts.ti.michael_natanael.DetailNewsActivity
import pnj.uts.ti.michael_natanael.R
import pnj.uts.ti.michael_natanael.ui.news.adapter.AdapterListBerita
import pnj.uts.ti.michael_natanael.ui.news.data.DataBerita

class NewsFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adatepter: AdapterListBerita
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.listView)
        adatepter = AdapterListBerita(requireActivity(), R.layout.item_list_layout)
        listView.adapter = adatepter
        buatData()

        listView.setOnItemClickListener { parent, _, position, _ ->
            val data = parent.getItemAtPosition(position) as DataBerita

            sharedPreferences =
                requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("imageBerita", data.imageUrl)
            editor.putString("textJudulBerita", data.title)
            editor.putString("textDeskripsiBerita", data.desc)
            editor.apply()

            val intent = Intent(requireActivity(), DetailNewsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buatData() {
        val arrayList = ArrayList<DataBerita>()

        for (i in 1..15) {
            val data1 = DataBerita()
            data1.imageUrl = "https://picsum.photos/300/20$i"
            data1.title = "Berita $i"
            data1.desc =
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
            arrayList.add(data1)
        }

        adatepter.addAll(arrayList)
        adatepter.notifyDataSetChanged()
    }
}